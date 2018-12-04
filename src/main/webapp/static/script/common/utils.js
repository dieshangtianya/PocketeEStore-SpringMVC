var webUtil = (function () {
    var webUtil = {};
    webUtil.isNullOrEmpty = function (value) {
        if (!value && value !== 0) {
            return true;
        }
        return false;
    };

    webUtil.isNumber = function (value) {
        if (webUtil.isNullOrEmpty(value)) {
            return false;
        }
        if (isNaN(value)) {
            return false;
        }
        return true;
    }

    webUtil.getURLParameter = function (parameterName) {
        var searchString = window.location.search;
        var reg = new RegExp("(^|&)" + parameterName + "=([^&]*)(&|$)");
        var r = searchString.substr(1).match(reg);
        if (r != null)
            return decodeURIComponent(r[2]);
        return null;
    };

    webUtil.DataMap = function () {
        var elementList = new Array();
        this.size = function () {
            return elementList.length;
        };
        this.isEmpty = function () {
            return elementList.length === 0;
        };

        this.clear = function () {
            elementList = new Array();
        };
        this.add = function (key, value) {
            this.remove(key);
            elementList.push({
                key: key,
                value: value,
            });
        };
        this.remove = function (key) {
            var index = elementList.findIndex(function (item) {
                return item.key === key;
            });
            if (index > -1) {
                elementList.splice(index, 1);
            }
            return true;
        };
        this.get = function (key) {
            var index = elementList.findIndex(function (item) {
                return item.key === key;
            });
            if (index > -1) {
                return elementList[index].value;
            }
            return null;
        };
        this.containsKey = function (key) {
            var index = elementList.findIndex(function (item) {
                return item.key === key;
            });
            if (index > -1) {
                return true;
            }
            return false;
        };
        this.containsValue = function (value) {
            for (var index = 0; index < this.size(); index += 1) {
                if (elementList[i].value === value) {
                    return true;
                }
            }
            return false;
        };

        this.values = function () {
            var values = [];
            for (var index = 0; index < this.size(); index += 1) {
                values.push(elementList[index].value);
            }
            return values;
        };

        this.keys = function () {
            var keys = [];
            for (var index = 0; index < this.size(); index += 1) {
                keys.push(elementList.key);
            }
            return keys;
        }

    };

    webUtil.DataMode = {
        ADD: 'add',
        UPDATE: 'update'
    };

    webUtil.getResourceUrl = function (relativePath) {
        return window.location.host + '/' + relativePath;
    };

    webUtil.isFunction = function isFunction(functionToCheck) {
        return (
            functionToCheck && {}.toString.call(functionToCheck) === '[object Function]'
        );
    };

    webUtil.isEmptyObject = function (obj) {
        var keys = Object.keys(obj);
        return keys.length === 0;
    };

    webUtil.formatToMoney = function (value) {
        if (!webUtil.isNumber(value)) {
            console.log('不是number');
            return '';
        }
        return Number.prototype.toFixed.call(value, 2);
    };

    webUtil.checkEmail = function (emailValue) {
        var emailExp = /^[A-Za-z\d]+([-_.][A-Za-z\d]+)*@([A-Za-z\d]+[-.])+[A-Za-z\d]{2,4}$/;
        if (emailExp.test(emailValue)) {
            return true;
        }
        return false;
    };

    webUtil.checkPhone = function (phoneValue) {
        var phoneExp = /^1[34578]\d{9}/;
        if (phoneExp.test(phoneValue)) {
            return true;
        }
        return false;
    };

    return webUtil;
})();