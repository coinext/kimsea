const MyApiProvider = /** @class */ (function () {

    function MyApiProvider() {
        this.simpleBaseProvider = new SimpleBaseProvider();
    }

    MyApiProvider.prototype.getMyApiInfo = function ($this, callback) {
        return this.simpleBaseProvider.get("/api/v1/myapi/getMyApiInfo", $this, callback, null);
    };

    return MyApiProvider;
}());

const myApiProvider = new MyApiProvider();