<script type="text/javascript">
const statsPanel = {
  template: `
    <div class="text-center bg-light" id="containerId">
      <div class="container mt-6">
        <div class="row">
          <div class="col">
            <notice-alert-view-component></notice-alert-view-component>
            <h1 v-if="type == 'users'" class="fs-2 fs-sm-4 fs-md-5 mt-6">유저 지표</h1>
            <h1 v-else-if="type == 'creators'" class="fs-2 fs-sm-4 fs-md-5 mt-6">작가 지표</h1>
            <h1 v-else-if="type == 'assets'" class="fs-2 fs-sm-4 fs-md-5 mt-6">NFTs 지표</h1>
            <p class="lead">대체 불가능한 토큰은 블록체인에 저장된 데이터 단위로, 고유하면서 상호 교환할 수 없는 토큰을 뜻합니다.<br/> NFT는 사진, 비디오, 오디오 및 기타 유형의 디지털 파일을 나타내는데 사용할 수 있고, 사본은 인정되지 않습니다.</p>
          </div>
        </div>
        <div class="container mt-6">
          <div v-if="type == 'users'" class="row align-content-center justify-content-center">
            <div class="table-responsive">
              <table class="table">
                <thead class="thead-light">
                <tr>
                  <th scope="col">#</th>
                  <th scope="col">유저명</th>
                  <th scope="col">소유 작품수</th>
                  <th scope="col">작품 판매수</th>
                  <th class="white-space-nowrap" scope="col">작품 판매액</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="(user,index) in users">
                  <th scope="row" v-text="(index + 1) + '.'"></th>
                  <td class="d-flex" v-on:click="onGoPageClick('/home#/owner/' + user.id + '/assets')">
                    <div class="avatar avatar-3xl mr-2">
                      <img class="rounded-circle border border-500 shadow-sm mr-1" :src="user.user.profileImgUrl" alt="" />
                    </div>
                    <span class="mt-3" v-text="user.user.profile"></span>
                  </td>
                  <td v-text="user.totalAssetCnt"></td>
                  <td v-text="user.totalSellAmount"></td>
                  <td v-text="user.totalSellPrice"></td>
                </tr>
                </tbody>
              </table>
            </div>
          </div>
          <div v-else-if="type == 'creators'" class="row align-content-center justify-content-center">
            <div class="table-responsive">
              <table class="table">
                <thead class="thead-light">
                <tr>
                  <th scope="col">#</th>
                  <th scope="col">작가명</th>
                  <th scope="col">발행 작품수</th>
                  <th scope="col">작품 판매수</th>
                  <th class="white-space-nowrap" scope="col">작품 판매액</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="(creator,index) in creators">
                  <th scope="row" v-text="(index + 1) + '.'"></th>
                  <td class="d-flex" v-on:click="onGoPageClick('/home#/creator/' + creator.id + '/assets')">
                    <div class="avatar avatar-3xl mr-2">
                      <img class="rounded-circle border border-500 shadow-sm mr-1" :src="creator.creator.profileImgUrl" alt="" />
                    </div>
                    <span class="mt-3" v-text="creator.creator.profile"></span>
                  </td>
                  <td v-text="creator.totalAssetCnt"></td>
                  <td v-text="creator.totalSellAmount"></td>
                  <td v-text="creator.totalSellPrice"></td>
                </tr>
                </tbody>
              </table>
            </div>
          </div>
          <div v-else="type == 'assets'" class="row align-content-center justify-content-center">
            <div class="table-responsive">
              <table class="table">
                <thead class="thead-light">
                <tr>
                  <th scope="col">#</th>
                  <th scope="col">작품명</th>
                  <th scope="col">가격</th>
                  <th scope="col">생성일</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="(asset,index) in assets">
                  <td scope="row" v-text="(index + 1) + '.'"></td>
                  <td class="d-flex" v-on:click="onGoPageClick('/home#/asset/' + asset.id)">
                    <div class="avatar avatar-3xl mr-2">
                      <img class="rounded-circle border border-500 shadow-sm mr-1" :src="asset.prevImgUrl" alt="" />
                    </div>
                    <span class="mt-3" v-text="asset.name"></span>
                  </td>
                  <td  v-text="asset.price"></td>
                  <td  v-text="readableDateString(asset.regDttm)"></td>
                </tr>
                </tbody>
              </table>
            </div>
          </div>
          <scroll-loader :loader-method="onLoad" :loader-disable="isDisableLoading">
            <div></div>
          </scroll-loader>
          <br/>
          <br/>
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
      users: [],
      creators:[],
      assets:[],
      pageNo : 0,
      pageSize : 30,
      type: this.$route.params.type,
      params : this.$route.params,
      isDisableLoading: false,
    }
  }
  , mounted : function() {
  }
  , created: function() {
    this.onLoad();
  }
  , methods : {
    onGoPageClick : function(url) {
      window.open(url, "_blank");
    },
    onLoad : function() {
      if (this.type == 'users') {
        statsProvider.getStatsUsers(this, function ($this, result) {
          $this.users = [...$this.users, ...result.data];
          $this.isDisableLoading = result.data.length < $this.pageSize;
        }, this.pageNo++, this.pageSize);
      } else if (this.type == 'creators') {
        statsProvider.getStatsCreators(this, function ($this, result) {
          $this.creators = [...$this.creators, ...result.data];
          $this.isDisableLoading = result.data.length < $this.pageSize;
        }, this.pageNo++, this.pageSize);
      } else if (this.type == 'assets') {
        statsProvider.getStatsAssets(this, function ($this, result) {
          $this.assets = [...$this.assets, ...result.data];
          $this.isDisableLoading = result.data.length < $this.pageSize;
        }, this.pageNo++, this.pageSize);
      }
    }
    ,readableDateString : function(date) {
      return utility.readableDateString(date);
    },
    isNew : function(date) {
      return utility.isNewContent(date);
    }
  }
};


</script>