<script type="text/javascript">
const noticeAlertViewComponent = {
  template: `<div v-if="isNew(notice.regDttm)"  class="alert alert-warning alert-dismissible mt-1 fade show" role="alert">
  <strong>공지사항!</strong> <span v-text="notice.title"></span>
  <button class="close" type="button" data-dismiss="alert" aria-label="Close"><span class="font-weight-light" aria-hidden="true">×</span></button>
  </div>`
  , data : function() {
    return {
      notice : {}
    }
  }
  , mounted : function() {
  }
  , created: function() {
    noticesProvider.getRecentlyNotice(this, function($this, result) {
      $this.notice = result.data;
    });
  }
  , methods : {
    readableDateString : function(date) {
      return utility.readableDateString(date);
    },
    isNew : function(date) {
      return utility.isNewContent(date);
    }
  }
};
</script>