package com.uplan.miyao.net.constant;

import com.uplan.miyao.net.Api;

/**
 * 所有接口地址按业务分类统一管理
 */

public class NetWorkConfig {

    /** 获取令牌token */
    public static final String GET_TOKEN = "/auth/csrf";

    /** 登录 */
    public static final String LOGIN = "/auth/ajaxLogin";

    /** 获取所有提交的表单数据 */
    public static final String ORDER_BD_DETAIL = "/tango2/bd/getBDDetailById/{id}";

    /** 获取用户数据 */
    public static final String GET_USER_INFO = "/auth/getUserInfo";

    /** 用户信息 */
    public static final String USER_INFO = "/auth/getUserInfo";

    /** 各状态数量 */
    public static final String STATUS_COUNT = "/tango2/bigData/findByOwnerWithDiffStateCount";

    /** 大数据提交*/
    public static final String BIGDATA_SUBMIT = "/tango2/bigData/submitBigDataAudit";
    /** 大数据修改*/
    public static final String BIGDATA_MODIFY = "/tango2/bigData/submitBigDataAudit/{id}";

    /** 修改密码 */
    public static final String CHANGE_MY_PASSWORD = "/auth/changeMyPassword";

    /** 工单查询*/
    public static final String WORK_SEARCH = "/tango2/bd/findBySearchKey/{size}/{page}/{searchkey}";

    /** 工单查询*/
    public static final String WORK_SEARCH_ALL = "/tango2/bd/findBySearchKey/{size}/{page}";

    /** 退出登录 */
    public static final String LOGIN_OUT = "/auth/logout";

    /** 大数据查询*/
    public static final String BIGDATA_QUERY = "/tango2/bd/listBizDataByStatus/{status}/{size}/{page}";

    /** 大数据（工单）详情查询*/
    public static final String BIGDATA_DETAILS_QUERY = ORDER_BD_DETAIL;

    /** 工单详情查询*/
    public static final String ORDER_DETAILS_QUERY = ORDER_BD_DETAIL;

    /** 工单列表 、待进件、合同资料、放款资料、放车资料 邮寄资料 */
    public static final String ORDER_GROUP = "/tango2/bd/listBizDataByStatus/{status}/{size}/{page}";

    /** 文件上传：视频，图片*/
    public static final String FILE_UPLOAD = "/tango2/bd/saveFile/{id}";

    /**进件资料上传*/
    public static final String PENDING_DATA_UPLOAD = "/tango2/bd/saveWord/{id}";

    /** 进件资料录入前详情获取*/
    public static final String PENDING_DATA_DETAIL = ORDER_BD_DETAIL;

    /** 获取图片、视频地址*/
    public static final String GET_FILE_URL = Api.BASE_URL + "/tango2/bd/getFile/";

    /** 获取图片、视频信息*/
    public static final String GET_MEDIA_DETAIL = Api.BASE_URL + "/tango2/bd/getBDDetailById/";

    /**提交状态*/
    public static final String CHANGE_ENTER_STATE="/tango2/bd/submitState/{id}/{state}";

    /**删除文件 */
    public static final String DELETE_FILE = "/tango2/bd/delWordOrFile/{id}/{key}";

    /** 版本更新接口 */
    public static final String UPDATE_VERSION = Api.BASE_URL + "/appVersion.json";

    /** App下载地址 */
    public static final String APK_DOWNLOAD_URL = Api.BASE_URL + "/zrx.apk";

    /**获取车商信息*/
    public static final String SELECT_CAR_DEALER = "/tango2/carDealer/findByNames";

    /** 复制下载地址链接 */
    public static final String COPY_LOAD_LINK = "tango2/contract/getOrGenerateContractDownloadInfo/{id}";

    /** 地址拼接*/
    public static final String LINK_APPEND = "/html/contract.html?id=";

    /** 获取和签地址*/
    public static final String HQ_ADDRESS = "/tango2/heqian/generateContractH5SignUrl/{bizDataId}/{type}";

    /** 和签结果页确认按钮地址*/
    public static final String HQ_CONFIRM_BUTTON_URL_PATH = "tango2/heqian/heqianResult";

    /** 和签结果页地址*/
    public static final String HQ_RESULT_URL_PATH = "the-contract/save";

    /** 下载合同*/
    public static final String HQ_DOWNLOAD_CONTRACT = "/tango2/heqian/heqianResult/{bizDataId}/{fileSn}/{type}";
}