<script type="text/javascript">
const eventViewComponent = {
  template: `<div class="card card-span h-100">
  <div class="card-body pt-3 pb-4">
    <ul class="list-group list-group-flush pt-0 mt-0">
      <div class="row">
        <div class="col-3 text-left">
          <span v-text="_event.name"></span>
        </div>
        <div class="col-9 text-left">
          <span v-text="_event.content"></span>
        </div>
      </div>
    </ul>
  </div>
  </div>`
  , props : ['_user','_event']
  , data : function() {
    return {
      user : this._user,
      _event: this._event
    }
  }
  , mounted : function() {

  }
  , created: function() {

  }
  , methods : {
    isNew : function(date) {
      return utility.isNewContent(date);
    }
  }
};
</script>