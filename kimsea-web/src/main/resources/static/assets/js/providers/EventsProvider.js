const EventsProvider = /** @class */ (function () {

    function EventsProvider() {
        this.simpleBaseProvider = new SimpleBaseProvider();
    }


    EventsProvider.prototype.getEvents = function ($this, callback, pageNo, pageSize) {
        return this.simpleBaseProvider.get("/api/v1/events/getEvents?pageNo=" + pageNo + "&pageSize=" + pageSize, $this, callback, null);
    };

    return EventsProvider;
}());

const eventsProvider = new EventsProvider();