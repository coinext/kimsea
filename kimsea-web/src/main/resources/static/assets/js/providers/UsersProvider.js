const UsersProvider = /** @class */ (function () {

    function UsersProvider() {
        this.simpleBaseProvider = new SimpleBaseProvider();
    }

    UsersProvider.prototype.getUserById = function ($this, callback, userId) {
        return this.simpleBaseProvider.get("/api/v1/users/getUserById?userId=" + userId, $this, callback, null);
    };

    return UsersProvider;
}());

const usersProvider = new UsersProvider();