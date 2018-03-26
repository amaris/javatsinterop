// This file was generated automatically with JavaTsInterop stub generator
var com;
(function (com) {
    var amaris;
    (function (amaris) {
        var javatsinterop;
        (function (javatsinterop) {
            var api;
            (function (api) {
                var TestResource = (function () {
                    function TestResource(baseUrl) {
                        if (baseUrl === void 0) { baseUrl = ''; }
                        this.baseUrl = baseUrl;
                    }
                    TestResource.prototype._xhr = function (method, url, callback) {
                        var xhr = new XMLHttpRequest();
                        xhr.open(method, url, true);
                        xhr.setRequestHeader('Content-type', 'application/json');
                        xhr.onload = function () { callback(JSON.parse(xhr.responseText)); };
                        xhr.send();
                    };
                    TestResource.prototype.helloWorld = function (callback) {
                        this._xhr('GET', this.baseUrl + 'test/hello', callback);
                    };
                    TestResource.prototype.tree = function (callback) {
                        this._xhr('GET', this.baseUrl + 'test/tree', callback);
                    };
                    return TestResource;
                }());
                api.TestResource = TestResource;
            })(api = javatsinterop.api || (javatsinterop.api = {}));
        })(javatsinterop = amaris.javatsinterop || (amaris.javatsinterop = {}));
    })(amaris = com.amaris || (com.amaris = {}));
})(com || (com = {}));
// This file was generated automatically with JavaTsInterop stub generator
var com;
(function (com) {
    var amaris;
    (function (amaris) {
        var javatsinterop;
        (function (javatsinterop) {
            var api;
            (function (api) {
                var ExampleResource = (function () {
                    function ExampleResource(baseUrl) {
                        if (baseUrl === void 0) { baseUrl = ''; }
                        this.baseUrl = baseUrl;
                    }
                    ExampleResource.prototype._xhr = function (method, url, callback) {
                        var xhr = new XMLHttpRequest();
                        xhr.open(method, url, true);
                        xhr.setRequestHeader('Content-type', 'application/json');
                        xhr.onload = function () { callback(JSON.parse(xhr.responseText)); };
                        xhr.send();
                    };
                    ExampleResource.prototype.tree = function (callback) {
                        this._xhr('GET', this.baseUrl + 'example/tree', callback);
                    };
                    ExampleResource.prototype.salaries = function (rank, discipline, callback) {
                        var url = this.baseUrl + 'example/salaries';
                        url += '?rank=' + (rank == null ? '' : encodeURIComponent(rank)) + '&discipline=' + (discipline == null ? '' : encodeURIComponent(discipline));
                        this._xhr('GET', url, callback);
                    };
                    return ExampleResource;
                }());
                api.ExampleResource = ExampleResource;
            })(api = javatsinterop.api || (javatsinterop.api = {}));
        })(javatsinterop = amaris.javatsinterop || (amaris.javatsinterop = {}));
    })(amaris = com.amaris || (com.amaris = {}));
})(com || (com = {}));
//# sourceMappingURL=stubs.js.map