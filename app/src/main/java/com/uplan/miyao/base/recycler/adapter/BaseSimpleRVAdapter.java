package com.uplan.miyao.base.recycler.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by double on 2016/11/1.
 */

public abstract class BaseSimpleRVAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected List<T> mDatas;
    protected OnItemClickListener mOnItemClickListener;
    protected Context mContext;

    // header
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;
    public static final int TYPE_FOOTER = 2;
    private View mHeaderView;
    private View mFooterView;

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    public void setFooterView(View footerView) {
        mFooterView = footerView;
        notifyItemInserted(getItemCount());
    }

    public View getFooterView() {
        return mFooterView;
    }

    public View getHeaderView() {
        return mHeaderView;
    }
    //

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null) return TYPE_NORMAL;
        if (position == getItemCount() - 1 && mFooterView != null) return TYPE_FOOTER;
        if (position == 0) return TYPE_HEADER;
        return TYPE_NORMAL;
    }


    public BaseSimpleRVAdapter() {
        mDatas = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }

        if (mHeaderView != null && viewType == TYPE_HEADER) return new Holder(mHeaderView);
        if (mFooterView != null && viewType == TYPE_FOOTER) return new Holder(mFooterView);

        return onCreateVH(parent, viewType);
    }

    public abstract RecyclerView.ViewHolder onCreateVH(ViewGroup parent, int viewType);


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) == TYPE_HEADER) return;
        if (getItemViewType(position) == TYPE_FOOTER) return;

        final int pos = getRealPosition(holder);
        T t = mDatas.get(pos);
        onBind(holder, position, t);

        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(holder, position, t);
                    }
                }
            });
        }
    }

    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        if (mHeaderView != null) {
            position--;
        }
        return position;
    }

    @Override
    public int getItemCount() {
        int ret = mDatas.size();
        if (mHeaderView != null) {
            ret++;
        }
        if (mFooterView != null) {
            ret++;
        }
        return ret;
    }


    public abstract void onBind(RecyclerView.ViewHolder viewHolder, int position, T data);

    //-----
    public void addAll(@NonNull List<? extends T> datas) {
        if (datas == null) return;
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    public void add(@NonNull T data) {
        if (data == null) {
            return;
        }
        mDatas.add(data);
        notifyItemChanged(mDatas.size());
    }

    public void clearAddAll(@NonNull List<T> datas) {
        mDatas.clear();
        addAll(datas);
    }


    public void addToFirst(T data) {
        mDatas.add(0, data);
        notifyItemInserted(0);
    }

    public void addAllToFirst(List<T> datas) {
        mDatas.addAll(0, datas);
        notifyDataSetChanged();
    }

    public void clear() {
        mDatas.clear();
        notifyItemRangeRemoved(0, mDatas.size());
    }

    public T getItemData(int position) {
        if (mDatas.size() > position) {
            T t = mDatas.get(position);
            return t;
        } else {
            return null;
        }
    }

    public List<T> getDatas() {
        return mDatas;
    }

    public void remove(int position) {
        if (getItemCount() > position) {
            mDatas.remove(position);
            notifyDataSetChanged();
        }
    }

    public void removeItem(int position) {
        if (getItemCount() > position) {
            mDatas.remove(position);
            notifyItemRemoved(position);
        }
    }
    //---


    public void setOnItemClickListener(OnItemClickListener li) {
        mOnItemClickListener = li;
    }

    public interface OnItemClickListener<T> {
        void onItemClick(RecyclerView.ViewHolder holder, int position, T data);
    }

    // attacth recyclerview
    protected RecyclerView mRecyclerView;

    public class Holder extends RecyclerView.ViewHolder {
        public Holder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;

        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return getItemViewType(position) == TYPE_HEADER
                            ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp != null
                && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
            p.setFullSpan(holder.getLayoutPosition() == 0);
        }
    }

}
