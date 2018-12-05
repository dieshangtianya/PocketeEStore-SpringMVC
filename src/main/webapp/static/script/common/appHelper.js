var APPHelper = (function () {
    //该模块主要提供与app相关的内容
    //其应该尽量与各种所用css或js框架无关。
    //其提供基本是各种函数。
    var appHelper = {};
    appHelper.APIMap = {
        SYSTEM_MENUS: '/api/management/menu/list/v1',
        ADMIN_LOGIN: '/api/management/admin/login/v1',
        ADMIN_LOGOUT: '/api/management/admin/logout/v1',
        ADMIN_LIST: '/api/management/admin/list/v1',
        ADMIN_ADD: '/api/management/admin/add/v1',
        ADMIN_UPDATE: '/api/management/admin/update/v1',
        ADMIN_DELETE: "/api/management/admin/delete/v1",
        ROLE_LIST: '/api/management/role/list/v1',
        ROLE_ADD: '/api/management/role/add/v1',
        ROLE_UPDATE: '/api/management/role/update/v1',
        ROLE_DELETE: '/api/management/role/delete/v1',
        FEATURE_LIST: '/api/management/feature/list/v1',
        PRODUCT_LIST: '/api/management/product/list/v1',
        PRODUCT_ADD: '/api/management/product/add/v1',
        PRODUCT_UPDATE: '/api/management/product/update/v1',
        PRODUCT_DELETE: '/api/management/product/delete/v1',
        ORDER_LIST: '/api/management/order/list/v1',
        PRODUCT_LIST_FE: '/api/product/list/v1',
        CUSTOMER_REGISTER: '/api/customer/register/v1',
        CUSTOMER_LOGIN: '/api/customer/login/v1',
        CUSTOMER_LOGOUT: '/api/customer/logout/v1',
        CUSTOMER_CHECK_LOGIN: '/api/customer/checkLogin/v1',
        CART_SYNC_DATA: '/api/cart/sync/v1',
        CART_ADD_ITEM: '/api/cart/addItem/v1',
        CART_REMOVE_ITEM: '/api/cart/removeItem/v1',
        CART_CHANGE_QUANTITY: '/api/cart/changeQuantity/v1',
        DO_ORDER: '/api/order/create/v1'
    };
    appHelper.HOME = '/';
    appHelper.MANAGEMENT_HOME = '/management';
    appHelper.MANAGEMENT_LOGIN = '/management/admin/signIn';
    appHelper.CUSTOMER_LOGIN = '/customer/signIn';
    return appHelper;
})();