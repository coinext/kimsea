<script type="text/javascript">
const assetsPanel = {
  template: `
    <div class="text-center bg-light" id="containerId">
    <div class="container mt-6">
      <div class="row">
        <div class="col">
          <notice-alert-view-component></notice-alert-view-component>
          <h1 class="fs-4 fs-sm-4 fs-md-5 mt-6" v-html="title"></h1>
          <h5 class="fs-1 fs-sm-1 fs-md-1 pl-5 pr-5 font-italic text-700 mb-6">NFT는 사진, 비디오, 오디오 및 기타 유형의 디지털 파일을 나타내는데 사용할 수 있습니다.</h5>
        </div>
      </div>
      <div v-if="user == null && type == 'MYASSETS'" class="row mt-6">
        <div  class="col-lg-12 mt-6 mb-6 mt-lg-0">
          <span class="mb-3">로그인을 하시면 볼수있습니다.</span><br/><br/>
          <a class="btn btn-outline-dark fs--1 border-2x rounded-pill mb-1" href="/login"><i class="fas fa-user-alt mr-1"></i>로그인하기</a>
        </div>
      </div>
      <div v-else class="row">
        <div  class="col-lg-3 mt-5 mb-6 mt-lg-0" v-for="asset in assets">
          <asset-view-component :_user="user" :_asset="asset"></asset-view-component>
        </div>
        <scroll-loader :loader-method="onLoad" :loader-disable="isDisableLoading">
          <div></div>
        </scroll-loader>
      </div>
    </div>
    </div>`
  , components : {
    'asset-view-component' : assetViewComponent,
    'notice-alert-view-component' : noticeAlertViewComponent,
  }
  , data : function() {
    return {
      user : _user,
      notice : {},
      assets:[],
      title: "",
      params : this.$route.params,
      query : this.$route.query,
      type: "",
      pageNo : 0,
      pageSize : 20,
      isDisableLoading: false,
    }
  }
  , created: function() {
    this.type = utility.getAssetRouteType(this.$route.path);
    this.onTitle(this.type);
  }
  , mounted : function() {
    var $this = this;
    $("#searchInputId").on('keypress',function(e) {
      const keycode = (e.keyCode ? e.keyCode : e.which);
      if(keycode == 13) {
        $this.pageNo = 0;
        $this.assets = [];
        $this.onLoad();
      }
    });
  }
  , methods : {
    onTitle : function(type) {
      if (type == 'ASSETS' ) {
        this.title = '<span class="fab fa-buffer mr-1"></span>' + _categories[this.params.category].title;
      } else if (type == 'MYASSETS') {
        this.title = '<span class="fab fa-buffer mr-1"></span>' + _status[this.params.status].title;
      } else if (type == 'OWNER') {
        usersProvider.getUserById(this, function ($this, result) {
          $this.title = utility.htmlAvator(result.data, result.data.profile + "'s 소유한 NFTs");
        }, this.params.userId);
      } else if (type == 'CREATOR') {
        usersProvider.getUserById(this, function ($this, result) {
          $this.title = utility.htmlAvator(result.data, result.data.profile + "'s 발행한 NFTs");
        }, this.params.userId);
      } else {

      }
    },
    onLoad : function() {

      if (this.user == null && this.type == 'MYASSETS') return;

      var query = '';
      if ($("#searchInputId") != 'undefined' && $("#searchInputId").val() != '') {
        query = $("#searchInputId").val();
        /*$("#searchInputId").val("");*/
      }

      if (this.type == 'ASSETS') {
        assetsProvider.getAssetsByCategory(this, function ($this, result) {
          $this.assets = [...$this.assets, ...result.data];
          $this.isDisableLoading = result.data.length < $this.pageSize;
        }, this.params.category, query, this.pageNo++, this.pageSize);
      } else if (this.type == 'MYASSETS') {
        assetsProvider.getMyAssetsByStatus(this, function($this, result) {
          $this.assets = [...$this.assets, ...result.data];
          $this.isDisableLoading = result.data.length < $this.pageSize;
        }, this.params.status, query, this.pageNo++, this.pageSize);
      } else if (this.type == 'OWNER') {
        assetsProvider.getAssetsByOwner(this, function($this, result) {
          $this.assets = [...$this.assets, ...result.data];
          $this.isDisableLoading = result.data.length < $this.pageSize;
        }, this.params.userId, query, this.pageNo++, this.pageSize);
      } else if (this.type == 'CREATOR') {
        assetsProvider.getAssetsByCreator(this, function($this, result) {
          $this.assets = [...$this.assets, ...result.data];
          $this.isDisableLoading = result.data.length < $this.pageSize;
        }, this.params.userId, query, this.pageNo++, this.pageSize);
      } else {

      }
    },
    isNew : function(date) {
      return utility.isNewContent(date);
    }
  }
};


</script>