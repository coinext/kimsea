<script type="text/javascript">
const eventsPanel = {
  template: `
    <div class="text-center bg-light" id="containerId">
      <div class="container mt-6">
        <div class="row">
          <div class="col">
            <notice-alert-view-component></notice-alert-view-component>
            <h1 class="fs-2 fs-sm-4 fs-md-5 mt-6">이벤트</h1>
            <p class="lead">대체 불가능한 토큰은 블록체인에 저장된 데이터 단위로, 고유하면서 상호 교환할 수 없는 토큰을 뜻합니다.<br/> NFT는 사진, 비디오, 오디오 및 기타 유형의 디지털 파일을 나타내는데 사용할 수 있고, 사본은 인정되지 않습니다.</p>
          </div>
        </div>
        <div class="container mt-6">
          <div class="row align-content-center justify-content-center">
            <div class="w-100 d-inline-flex position-relative py-6 py-lg-8" v-for="event in events">
              <div class="bg-holder rounded-soft overlay overlay-0" style="background-image:url(https://img4.daumcdn.net/thumb/R658x0.q70/?fname=https://t1.daumcdn.net/news/202201/14/pressian/20220114003905071zubx.jpg);">
              </div>
              <div class="position-relative text-center">
                <h4 class="text-white">Image Background</h4>
              </div>
            </div>
          </div>
          <scroll-loader :loader-method="onLoad" :loader-disable="isDisableLoading">
            <div></div>
          </scroll-loader>
        </div>
      </div>
    </div>`
  , components : {
    'event-view-component' : eventViewComponent,
    'notice-alert-view-component' : noticeAlertViewComponent
  }
  , data : function() {
    return {
      user : _user,
      events:[],
      params : this.$route.params,
      pageNo : 0,
      pageSize : 20,
      isDisableLoading: false,
    }
  }
  , mounted : function() {
  }
  , created: function() {
  }
  , methods : {
    onLoad : function() {
      eventsProvider.getEvents(this, function ($this, result) {
        $this.events = [...$this.events, ...result.data];
        $this.isDisableLoading = result.data.length < $this.pageSize;
      }, this.pageNo++, this.pageSize);
    }
  }
};


</script>