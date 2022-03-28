<script type="text/javascript">
const assetViewComponent = {
  template: `<div class="card card-span h-100 ml-3 mr-3">
  <span v-if="isNew(asset.regDttm)" class="badge badge-pill badge-danger position-absolute r-0 t-0 mt-2 mr-2 z-index-2">New</span>
  <video v-if="asset.mime == 'VIDEO'"
         class="video-js vjs-default-skin card-img-top fix-h-asset-video-img"
         width="640px"
         height="640px"
         controls preload="none"
         :poster="asset.prevImgUrl"
         data-setup='{ "aspectRatio":"640:640", "playbackRates": [1, 1.5, 2] }'>
    <source :src="asset.prevAttachFileUrl" type="video/mp4" />
  </video>
  <video v-else-if="asset.mime == 'AUDIO'"
         class="video-js vjs-default-skin card-img-top fix-h-asset-video-img"
         width="640px"
         height="640px"
         controls preload="none"
         :poster="asset.prevImgUrl"
         data-setup='{ "aspectRatio":"640:640", "playbackRates": [1, 1.5, 2] }'>
    <source :src="asset.prevAttachFileUrl" type="audio/mp3" />
  </video>
  <model-viewer v-else-if="asset.mime == 'MODEL'" id="lazy-load" reveal="interaction"  class="card-img-top fix-h-asset-img" :src="asset.prevAttachFileUrl" ar ar-modes="webxr scene-viewer quick-look" camera-controls environment-image="neutral" shadow-intensity="1" autoplay>
    <div id="lazy-load-poster" slot="poster"><img class="card-img-top fix-h-asset-video-img" :src="asset.prevImgUrl"></div>
    <div id="button-load" class="btn btn-outline-primary rounded-capsule" slot="poster"><span class="fas fa-play mr-1"></span> 3D 모델보기</div>
  </model-viewer>
  <model-viewer v-else-if="asset.mime == 'MODEL2'" id="lazy-load" reveal="interaction"  class="card-img-top fix-h-asset-img" :src="asset.attachFileUrl" ar ar-modes="webxr scene-viewer quick-look" environment-image="https://modelviewer.dev/shared-assets/environments/moon_1k.hdr" seamless-poster shadow-intensity="1" camera-controls autoplay>
    <div class="progress-bar hide" slot="progress-bar">
      <div class="update-bar"></div>
    </div>
    <div id="lazy-load-poster" slot="poster"><img class="card-img-top fix-h-asset-video-img" :src="asset.prevImgUrl"></div>
    <div id="button-load" class="btn btn-outline-primary rounded-capsule" slot="poster"><span class="fas fa-play mr-1"></span> 3D 모델보기</div>
  </model-viewer>
  <img v-else-if="asset.mime == 'IMAGE'" class="card-img-top fix-h-asset-img" :src="asset.prevImgUrl">
  <img v-else class="card-img-top fix-h-asset-img" :src="asset.prevImgUrl">
  <div class="card-body pt-2 pb-1">
    <ul class="list-group list-group-flush pt-0 mt-0">
      <div class="row">
        <div class="col-3 text-left">
          <a class="avatar avatar-3xl mt-2" :href="'/home#/owner/' + asset.owner.id + '/assets'">
            <img class="rounded-circle border border-500 shadow-sm" :src="asset.owner.profileImgUrl" alt="" />
          </a>
        </div>
        <div class="col-9 text-left">
          <a :href="'/home#/asset/' + asset.id" target="_blank">
            <h5 class="card-title mb-2 mt-1" v-text="asset.name + ' #' + asset.id"></h5>
          </a>
          <p class="fs--1">
            <a :href="'/home#/assets/' + asset.category">
              <span class="badge badge-pill badge-secondary mr-1 p-1" v-text="asset.category"></span>
            </a>, made by
            <a :href="'/home#/creator/' + asset.creator.id + '/assets'">
              <span class="text-info font-weight-bold" v-text="asset.creator.profile"></span>
            </a>
          </p>
        </div>
      </div>
    </ul>
    <ul class="list-group list-group-flush pt-0 mt-0">
      <li class="list-group-item pt-0 pb-0 mt-2 mb-0"></li>
      <li class="list-group-item">
        <div class="d-flex align-items-center justify-content-between">
          <div>
            <span class="far fa-clock"></span>
            <span v-html="readableDateString(asset.regDttm)"></span>
          </div>
          <div>
            <i class="fas fa-gem mr-2 ml-2"></i>
            <span v-if="asset.sellOrder != null" class="text-dark font-weight-bold" v-text="asset.sellOrder.price"></span>
            <span v-else-if="asset.price != null && asset.price > 0" class="text-dark font-weight-bold" v-text="asset.price"></span>
            <span v-else class="text-dark font-weight-bold">미정</span>
          </div>
        </div>
      </li>
      <li v-if="asset.bidEndDttm != null" class="list-group-item">
        <vue-countdown :endDate="asset.bidEndDttm"></vue-countdown>
      </li>
      <li v-else class="list-group-item">
        아직 입찰이 시작되지않았습니다.
      </li>
      <li class="d-sm-none d-md-block list-group-item">
        <p class="card-text multiline-ellipsis" v-text="asset.description"></p>
      </li>
      <li v-if="user != null" class="list-group-item">
        <div class="d-flex justify-content-end">
          <button v-if="user != null && user.id != asset.owner.id && asset.buyOrder != null && asset.status != 'WITHDRAW' && asset.status != 'WITHDRAWING'" class="btn btn-success fs--1 border-2x rounded-pill ml-2" v-on:click="onCancelBuyNftClick"><span class="fas fa-times mr-1"></span>구매취소하기</button>
          <button v-if="user != null && user.id != asset.owner.id && asset.buyOrder == null && asset.status != 'WITHDRAW' && asset.status != 'WITHDRAWING'" class="btn btn-success fs--1 border-2x rounded-pill ml-2" v-on:click="onBuyNftClick"><span class="far fa-arrow-alt-circle-left mr-1"></span>구매하기</button>
          <button v-if="user != null && user.id == asset.owner.id && asset.sellOrder == null && asset.status != 'WITHDRAW' && asset.status != 'WITHDRAWING'" class="btn btn-danger fs--1 border-2x rounded-pill ml-2" v-on:click="onSellNftClick"><span class="far fa-arrow-alt-circle-right mr-1"></span>판매하기</button>
          <button v-if="user != null && user.id == asset.owner.id && asset.sellOrder != null && asset.status != 'WITHDRAW' && asset.status != 'WITHDRAWING'" class="btn btn-danger fs--1 border-2x rounded-pill ml-2" v-on:click="onCancelSellNftClick"><span class="fas fa-times mr-1"></span>판매취소하기</button>
          <button v-if="user != null && user.id == asset.owner.id && asset.sellOrder == null && asset.status != 'WITHDRAW' && asset.status != 'WITHDRAWING'" class="btn btn-outline-dark fs--1 border-2x rounded-pill ml-2" v-on:click="onWithdrawNftClick"><span class="fas fa-share-square mr-1"></span>출금하기</button>
          <button v-if="user != null && user.id == asset.owner.id && (asset.status == 'WITHDRAW' || asset.status == 'WITHDRAWING')" class="btn btn-outline-dark fs--1 border-2x rounded-pill ml-2" v-on:click="onViewWithdrawNftClick(asset.withdrawTx)"><span class="far fa-file-alt mr-1"></span>출금 정보보기</button>
        </div>
      </li>
    </ul>
  </div>
  </div>`
  , props : ['_user','_asset']
  , components : {
    'vue-countdown' : countdownViewComponent,
  }
  , data : function() {
    return {
      user : this._user,
      asset: this._asset
    }
  }
  , mounted : function() {

  }
  , created: function() {

  }
  , methods : {
    onWithdrawNftClick : function() {
      utility.componentPopup("NFT 출금하기", popupWithdrawNftViewComponent, {
        user : this.user,
        asset : this.asset,
        wallet : {},
        toAddress : null,
        agreement : true,
        isLoading : false,
      }, "withdraw-popup-show");
    },

    onIssueNftClick : function() {
      utility.componentPopup("NFT 발행하기", popupMintNftViewComponent, {
        user : this.user,
        asset : this.asset,
        wallet : {},
        agreement : true,
        isLoading : false,
      });
    },

    onCancelBuyNftClick : function() {
      utility.componentPopup("NFT 구매취소하기", popupCancelBuyNftViewComponent, {
        user : this.user,
        asset : this.asset,
        wallet : {},
        orders : {},
        price : this.asset.buyOrder.price,
        agreement : true,
        isLoading : false,
      }, 'sell-buy-popup-show');
    },
    onBuyNftClick : function() {
      utility.componentPopup("NFT 구매하기", popupBuyNftViewComponent, {
        user : this.user,
        asset : this.asset,
        wallet : {},
        orders : {},
        price : this.asset.price,
        agreement : true,
        isLoading : false,
      }, 'sell-buy-popup-show');
    },
    onCancelSellNftClick : function() {
      utility.componentPopup("<strong>NFT 판매취소하기</strong>", popupCancelSellNftViewComponent, {
        user : this.user,
        asset : this.asset,
        wallet : {},
        orders : {},
        price : this.asset.sellOrder.price,
        agreement : true,
        isLoading : false,
      }, 'sell-buy-popup-show');
    },
    onSellNftClick : function() {
      utility.componentPopup("<strong>NFT 판매하기</strong>", popupSellNftViewComponent, {
        user : this.user,
        asset : this.asset,
        wallet : {},
        orders : {},
        price : this.asset.price,
        agreement : true,
        isLoading : false,
      }, "sell-buy-popup-show");
    },
    onViewWithdrawNftClick : function(withdrawTx) {
      var content = "정보가 없습니다";
      if (withdrawTx != null) {
        if (withdrawTx.status == "PROCESS") {
          content = "TXID : <br/><a href='http://etherscan.io/tx/" + withdrawTx.orgTx + "' target='_blank'>" + withdrawTx.orgTx + "</a><br/><br/>보낸주소 : <br/><a href='https://etherscan.io/address/" + withdrawTx.toAddress + "' target='_blank'>" + withdrawTx.toAddress + "</a><br/><br/>요청시간 : <br/>" + withdrawTx.regDttm + "<br/><br/>";
        } else if (withdrawTx.status == "PENDING") {
          content = "<br/>보낸주소 : <br/><a href='https://etherscan.io/address/" + withdrawTx.toAddress + "' target='_blank'>" + withdrawTx.toAddress + "</a><br/><br/>요청시간 : <br/>" + withdrawTx.regDttm + "<br/><br/>";
        } else if (withdrawTx.status == "COMPLETED") {
          content = "TXID : <br/><a href='http://etherscan.io/tx/" + withdrawTx.orgTx + "' target='_blank'>" + withdrawTx.orgTx + "</a><br/><br/>보낸주소 : <br/><a href='https://etherscan.io/address/" + withdrawTx.toAddress + "' target='_blank'>" + withdrawTx.toAddress + "</a><br/><br/>요청시간 : <br/>" + withdrawTx.regDttm + "<br/><br/>완료시간 : <br/>" + withdrawTx.completedDttm + "<br/><br/>";
        }
      }
      utility.successAlert("NFT 출금정보", "<div class='text-left'>" +  content + "</div>", function() {});
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