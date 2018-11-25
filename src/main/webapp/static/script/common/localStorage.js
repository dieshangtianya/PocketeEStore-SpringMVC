var LocalStorage = (function () {

    var getDataType = function (object) {
        return (Object.prototype.toString.call(object).match(/^\[object\s(.*)\]$/)[1]);
    };

    var storage = window.localStorage;

    var localStorage = {};
    localStorage.getItem = function (key) {
        if (!storage) {
            return null;
        }
        var value = storage.getItem(key);
        if (value && (value.substring(0, 1) === '{' || value.substring(0, 1) === '[')) {
            value = JSON.parse(value);
        }
        return value;
    };

    localStorage.setItem = function (key, value) {
        if (!storage) {
            return false;
        }
        if (getDataType(value) === 'object' || getDataType(value) === 'Array') {
            value = JSON.stringify(value);
        }
        storage.setItem(key, value);
        return true;
    };

    localStorage.removeItem = function (key) {
        if (!storage) {
            return false;
        }
        if (localStorage.getItem(key)) {
            storage.removeItem(key);
        }
        return true;
    };

    return localStorage;
})();