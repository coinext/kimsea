<script type="text/javascript">
const popupCancelSellNftViewComponent = {
  template: `
    <div id="sellNftPopup" xmlns="http://www.w3.org/1999/html">
    <div class="fix-m-4" role="document">
      <div class="modal-content">
        <div class="modal-body">
          <div class="d-md-none d-sm-block row d-flex no-gutters">
            <div class="col pl-lg-2 mb-3">
              <div class="card h-100 text-left">
                <div class="bg-light card-body">
                  <h5><span class="fas fa-wallet mr-1"></span>내 지갑현황</h5>
                  <div class="card">
                    <div class="card-body p-2">
                      <div class="btn-sm btn-using-balance mb-0 rounded rounded-capsule pl-2 pr-2 mb-1">
                        <i class="fas fa-gem mr-2 ml-2"></i>사용중 GEM : <span class="ml-3 mr-3" v-text="wallet.usingGem"></span>
                      </div>
                      <div class="btn-sm btn-available-balance mb-0 rounded rounded-capsule pl-2 pr-2">
                        <i class="fas fa-gem mr-2 ml-2"></i>사용가능 GEM : <span class="ml-3 mr-3" v-text="wallet.availableGem"></span>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="row d-flex no-gutters">
            <div class="d-sm-none d-md-block  col-3 pl-lg-2 mb-3">
              <div class="card h-100 text-left">
                <div class="d-sm-none d-md-block">
                  <div class="bg-light card-body">
                    <h5>NFT</h5>
                    <img class="lazyload card-img-top h-auto" :src="asset.prevImgUrl" v-bind:data-src="asset.imgUrl">
                    <hr>
                    <h5>설명</h5>
                    <p class="card-text multiline-ellipsis" v-text="asset.description"></p>
                    <hr>
                  </div>
                </div>
              </div>
            </div>
            <div class="col pl-lg-2 mb-3">
              <div class="card h-100 text-left">
                <div class="bg-light card-body">
                  <h5><span class="far fa-file-alt mr-1"></span>현재 입찰 목록</h5>
                  <hr>
                  <ul class="list-group">
                    <li class="list-group-item d-flex justify-content-between align-items-center" v-for="order in orders">
                      <div class="d-sm-none d-md-block d-flex align-items-center">
                        <div class="avatar avatar-3xl mr-2">
                          <img class="rounded-circle border border-500 shadow-sm mr-1" :src="order.from.profileImgUrl" alt="" />
                        </div>
                        <span class="text-info font-weight-bold" v-text="order.from.profile"></span>
                      </div>
                      <span class="badge badge-soft-primary badge-pill" v-text="order.price + ' GEM'"></span>
                    </li>
                  </ul>
                </div>
              </div>
            </div>
            <div class="col pl-lg-2 mb-3">
              <div class="card h-100 text-left">
                <div class="bg-light card-body">
                  <div class="d-sm-none d-md-block">
                    <h5><span class="fas fa-wallet mr-1"></span>내 지갑현황</h5>
                    <div class="card">
                      <div class="card-body form-group">
                        <div class="btn-sm btn-using-balance mb-0 rounded rounded-capsule pl-2 pr-2 mb-1">
                          <i class="fas fa-gem mr-2 ml-2"></i>사용중 GEM : <span class="ml-3 mr-3" v-text="wallet.usingGem"></span>
                        </div>
                        <div class="btn-sm btn-available-balance mb-0 rounded rounded-capsule pl-2 pr-2">
                          <i class="fas fa-gem mr-2 ml-2"></i>사용가능 GEM : <span class="ml-3 mr-3" v-text="wallet.availableGem"></span>
                        </div>
                      </div>
                    </div>
                    <hr>
                  </div>
                  <h5><span class="far fa-copyright mr-1"></span>판매가격</h5>
                  <div class="form-group">
                    <input class="form-control" v-model="price" placeholder="가격을 입력하세요" type="number"/>
                  </div>
                  <hr>
                  <h5><span class="fab fa-adobe mr-1"></span>주의사항</h5>
                  <p class="card-text multiline-ellipsis">주의사항입니다.주의사항입니다주의사항입니다주의사항입니다주의사항입니다주의사항입니다주의사항입니다</p>
                  <div class="form-group form-check">
                    <input class="form-check-input" id="agreeCheckBox" type="checkbox" v-model="agreement">
                    <label class="form-check-label" for="agreeCheckBox">동의합니다.</label>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="modal-footer">
          <button class="btn btn-secondary btn-sm" type="button" v-on:click="onClosePopupClick()">닫기</button>
          <button class="btn btn-primary btn-sm" type="button" v-on:click="onModifySellNftClick()">판매수정하기</button>
          <button class="btn btn-primary btn-sm" type="button" v-on:click="onCancelSellNftClick()">판매취소하기</button>
        </div>
      </div>
    </div>
    <div :class="{'wrap-loading':true, 'display-none':!isLoading}">
      <div class="spinner-border text-info text-center" role="status">
        <span class="sr-only">Loading...</span>
      </div>
    </div>
    </div>`
  , el: '#sellNftPopup'
  , data : function() {
    return {
      user : {},
      asset : {},
      wallet : {},
      orders : {},
      price : this.asset.price,
      agreement : false,
      isLoading : false,
    }
  }
  , mounted : function() {
  }
  , created: function() {
    this.onWalletLoad();
  }
  , methods : {
    onWalletLoad : function() {
      walletsProvider.getMyWallet(this, function ($this, result) {
        $this.wallet = result.data;
      });

      ordersProvider.getOrdersByAssetIdAndStatus(this, function ($this, result) {
        $this.orders = result.data;
      }, this.asset.id, 'BUY');
    },
    onClosePopupClick : function() {
      swal.close();
    },
    onModifySellNftClick : function () {
      if (this.agreement != true) {
        utility.errorAlert("", "주의사항을 읽고 동의를 해주세요");
        return;
      }
      this.isLoading = true;
      assetsProvider.modifySellAsset(this, function ($this, result) {
        $this.isLoading = false;
        if (result.code == "C0000") {
          utility.successAlert("", "판매수정 성공", function () {
            window.location.reload();
          });
        } else {
          utility.errorCodeAlert(result.code, result.msg, function () {
            window.location.reload();
          });
        }
      }, this.asset.id, this.price);
    },

    onCancelSellNftClick : function() {
      if (this.agreement != true) {
        utility.errorAlert("", "주의사항을 읽고 동의를 해주세요");
        return;
      }
      this.isLoading = true;
      assetsProvider.cancelSellAsset(this, function ($this, result) {
        $this.isLoading = false;
        if (result.code == "C0000") {
          utility.successAlert("", "판매취소 성공", function () {
            window.location.reload();
          });
        } else {
          utility.errorCodeAlert("판매취소 실패", result.code, result.msg, function () {
            window.location.reload();
          });
        }
      }, this.asset.id);
    }
  }
};
</script>