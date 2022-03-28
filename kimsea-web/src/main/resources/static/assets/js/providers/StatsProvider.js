const StatsProvider = /** @class */ (function () {

    function StatsProvider() {
        this.simpleBaseProvider = new SimpleBaseProvider();
    }


    StatsProvider.prototype.getStatsUsers = function ($this, callback, pageNo, pageSize) {
        return this.simpleBaseProvider.get("/api/v1/stats/getStatsUsers" + "?pageNo=" + pageNo + "&pageSize=" + pageSize, $this, callback, null);
    };

    StatsProvider.prototype.getStatsCreators = function ($this, callback, pageNo, pageSize) {
        return this.simpleBaseProvider.get("/api/v1/stats/getStatsCreators"+ "?pageNo=" + pageNo + "&pageSize=" + pageSize, $this, callback, null);
    };

    StatsProvider.prototype.getStatsAssets = function ($this, callback, pageNo, pageSize) {
        return this.simpleBaseProvider.get("/api/v1/stats/getStatsAssets"+ "?pageNo=" + pageNo + "&pageSize=" + pageSize, $this, callback, null);
    };

    return StatsProvider;
}());

const statsProvider = new StatsProvider();