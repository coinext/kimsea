const NoticesProvider = /** @class */ (function () {

    function NoticesProvider() {
        this.simpleBaseProvider = new SimpleBaseProvider();
    }

    NoticesProvider.prototype.getNotices = function ($this, callback, pageNo, pageSize) {
        return this.simpleBaseProvider.get("/api/v1/notices/all?" + "pageNo=" + pageNo + "&pageSize=" + pageSize, $this, callback, null);
    };

    NoticesProvider.prototype.getRecentlyNotice = function ($this, callback) {
        return this.simpleBaseProvider.get("/api/v1/notices/recently", $this, callback, null);
    };

    return NoticesProvider;
}());

const noticesProvider = new NoticesProvider();