<script type="text/javascript">
const noticesPanel = {
  template: `
    <div class="text-center bg-light" id="containerId">
      <div class="container mt-6">
        <div class="row">
          <div class="col">
            <h1 class="fs-2 fs-sm-4 fs-md-5 mt-6">공지사항</h1>
            <p class="lead">대체 불가능한 토큰은 블록체인에 저장된 데이터 단위로, 고유하면서 상호 교환할 수 없는 토큰을 뜻합니다.</p>
          </div>
        </div>
        <div class="row mt-6">
          <div class="col mt-6 mb-6 mt-lg-0">
            <div class="accordion border-x border-top rounded" id="accordionNotices">
              <div class="card shadow-none border-bottom" v-for="(notice, index) in notices">
                <div class="card-header py-0" :id="'headingOne' + notice.id">
                  <div class="btn btn-link text-decoration-none btn-block py-2 px-0 text-left" type="button" data-toggle="collapse" :data-target="'#collapseOne' + notice.id" aria-expanded="true" :aria-controls="'collapseOne' + notice.id">
                    <span class="fas fa-caret-right accordion-icon mr-3" data-fa-transform="shrink-2"></span>
                    <span v-if="isNew(notice.regDttm)" class="badge badge-pill badge-danger">NEW</span>
                    <span class="font-weight-medium text-sans-serif text-900" v-html="notice.title"></span>
                    <div class="text-right text-900">
                      <span class="far fa-clock"></span>
                      <span v-html="readableDateString(notice.regDttm)"></span>
                    </div>
                  </div>
                </div>
                <div :class="{collapse:true, show:index == 0}" :id="'collapseOne' + notice.id" :aria-labelledby="'headingOne' + notice.id" data-parent="#accordionNotices">
                  <div class="card-body pt-2 text-left border-dashed-top">
                    <div class="pl-4" v-html="notice.content"></div>
                  </div>
                </div>
              </div>
              <scroll-loader :loader-method="onLoad" :loader-disable="isDisableLoading">
                <div></div>
              </scroll-loader>
            </div>
          </div>
        </div>
      </div>
    </div>`
  , data : function() {
    return {
      user : _user,
      params : this.$route.params,
      pageNo : 0,
      pageSize : 10,
      isDisableLoading: false,
      notices: []
    }
  }
  , mounted : function() {
  }
  , created: function() {

  }
  , methods : {
    onLoad : function() {
      noticesProvider.getNotices(this, function($this, result) {
        $this.notices = [...$this.notices, ...result.data];
        $this.isDisableLoading = result.data.length < $this.pageSize;
      }, this.pageNo++, this.pageSize);
    },
    readableDateString : function(date) {
      return utility.readableDateString(date);
    },
    isNew : function(date) {
      return utility.isNewContent(date);
    }
  }
};


</script>