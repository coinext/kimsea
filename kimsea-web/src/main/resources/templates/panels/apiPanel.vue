<script type="text/javascript">
const apiPanel = {
  template: `
    <div class="text-center bg-light" id="containerId">
    <div class="container mt-6">
      <div class="row">
        <div class="col">
          <notice-alert-view-component></notice-alert-view-component>
          <h1 class="fs-2 fs-sm-4 fs-md-5 mt-6">내 API 설정</h1>
          <p class="lead">APIKEY를 사용하여, 사용자의 어플레케이션에서 마음껏 kimsea기능을 사용해보세요<br/>단, API사용을 위해서는 메일로 사용신청을 해주셔야 사용할수있습니다.<br/>
            API 신청 메일주소 : (mybigstory@hanmail.net)</p>
        </div>
      </div>
      <div v-if="user == null" class="row mt-6">
        <div class="col-lg-12 mt-6 mb-6 mt-lg-0">
          <span class="mb-3">로그인을 하시면 볼수있습니다.</span><br/><br/>
          <a class="btn btn-outline-dark fs--1 border-2x rounded-pill mb-1" href="/login"><i
              class="fas fa-user-alt mr-1"></i>로그인하기</a>
        </div>
      </div>
      <div v-else>
        <div class="row mt-6">
          <div class="col-lg-12 mt-6 mb-3 mt-lg-0">
            <div class="card card-span h-100">
              <div class="card-body pt-4 pb-1">
                <ul class="list-group list-group-flush pt-0 mt-0 pb-3">
                  <h5 class="card-title text-left mb-2 mt-1"># 내 API KEY</h5>
                  <li class="list-group-item text-left">
                    <h4 class="card-text" v-text="apiInfo.apiKey"></h4>
                  </li>
                </ul>
              </div>
            </div>
          </div>
        </div>
        <div class="row mt-1">
          <div class="col-lg-12 mt-2 mb-6 mt-lg-0">
            <div class="card card-span h-100">
              <div class="card-body pt-4 pb-1">
                <ul class="list-group list-group-flush pt-0 mt-0 pb-3">
                  <h5 class="card-title text-left mb-2 mt-1"># 내 API 사용량</h5>
                  <li class="list-group-item text-left">
                    <h4 class="card-text" v-text="'오늘 현재 총 ' + apiInfo.quata + '건의 호출이 가능합니다.(하루마다 초기화됨)'"></h4>
                  </li>
                </ul>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    </div>`
  , components : {
    'notice-alert-view-component' : noticeAlertViewComponent,
  }
  , data : function() {
    return {
      user : _user,
      notice : {},
      apiInfo : {},
      params : this.$route.params,
      query : this.$route.query,
    }
  }
  , created: function() {
    this.onApiLoad();
  }
  , methods : {
    onApiLoad : function() {
      if (this.user == null) return;

      myApiProvider.getMyApiInfo(this, function($this, result) {
        $this.apiInfo = result.data;
      });
    },
    isNew : function(date) {
      return utility.isNewContent(date);
    },
    readableDateString : function(date) {
      return "<span class='ml-2 mr-1 far fa-clock'></span>" + utility.readableDateString(date);
    }
  }
};


</script>