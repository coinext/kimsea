<script type="text/javascript">
const assetPanel = {
  template: `
    <div class="text-center bg-light" id="containerId">
      <div class="container mt-6">
        <div class="row">
          <div class="col">
            <notice-alert-view-component></notice-alert-view-component>
            <h1 class="fs-2 fs-sm-4 fs-md-5 mt-6" v-text="asset.name + ' #' + asset.id"></h1>
            <p class="lead font-italic">NFT는 사진, 비디오, 오디오 및 기타 유형의 디지털 파일을 나타내는데 사용할 수 있고, 사본은 인정되지 않습니다.</p>
          </div>
        </div>
        <div class="row mt-6">
          <div class="col-lg-5 mt-6 mb-6 mt-lg-0">
            <div class="card card-span h-100 ml-3 mr-3">
              <span v-if="isNew(asset.regDttm)" class="badge badge-pill badge-danger position-absolute r-0 t-0 mt-2 mr-2 z-index-2">New</span>
              <video v-if="asset.mime == 'VIDEO'"
                     class="video-js vjs-default-skin card-img-top h-auto"
                     width="640px"
                     height="640px"
                     controls preload="none"
                     :poster="asset.prevImgUrl"
                     data-setup='{ "aspectRatio":"640:640", "playbackRates": [1, 1.5, 2] }'>
                <source :src="asset.attachFileUrl" type="video/mp4" />
              </video>
              <video v-else-if="asset.mime == 'AUDIO'"
                     class="video-js vjs-default-skin card-img-top h-auto"
                     width="640px"
                     height="640px"
                     controls preload="none"
                     :poster="asset.prevImgUrl"
                     data-setup='{ "aspectRatio":"640:640", "playbackRates": [1, 1.5, 2] }'>
                <source :src="asset.attachFileUrl" type="audio/mp3" />
              </video>
              <model-viewer v-else-if="asset.mime == 'MODEL'" class="card-img-top fix-h-asset-img" :src="asset.prevAttachFileUrl" ar ar-modes="webxr scene-viewer quick-look" environment-image="https://modelviewer.dev/shared-assets/environments/moon_1k.hdr" :poster="asset.prevImgUrl" seamless-poster shadow-intensity="1" camera-controls autoplay></model-viewer>
              <img v-else-if="asset.mime == 'IMAGE'" class="lazyload card-img-top h-auto" :src="asset.prevImgUrl" v-bind:data-src="asset.imgUrl">
              <img v-else class="lazyload card-img-top h-auto" :src="asset.prevImgUrl" v-bind:data-src="asset.imgUrl">
              <div class="card-body pt-3 pb-4">
                <ul class="list-group list-group-flush pt-0 mt-0">
                  <h5 class="card-title text-left mb-2 mt-1"># NFT 설명</h5>
                  <li class="list-group-item text-left">
                    <p class="card-text multiline-ellipsis" v-text="asset.description"></p>
                  </li>
                  <h5 class="card-title text-left mb-2 mt-1"># NFT 속성</h5>
                  <li class="list-group-item text-left">
                    <div>
                      <div class="card h-100">
                        <div class="bg-light card-body">
                          <div class="form-row">
                            <div class="col-3 col-sm-3">
                              <div class="form-group">
                                <input class="form-control"  placeholder="속성명" type="text" :value="attrs[0].trait_type" disabled>
                              </div>
                            </div>
                            <div class="col-3 col-sm-3">
                              <div class="form-group">
                                <input class="form-control"  placeholder="속성값" type="text" :value="attrs[0].value" disabled>
                              </div>
                            </div>
                            <div class="col-3 col-sm-3">
                              <div class="form-group">
                                <input class="form-control"  placeholder="속성명" type="text" :value="attrs[1].trait_type" disabled>
                              </div>
                            </div>
                            <div class="col-3 col-sm-3">
                              <div class="form-group">
                                <input class="form-control"  placeholder="속성값" type="text" :value="attrs[1].value" disabled>
                              </div>
                            </div>
                          </div>
                          <div class="form-row">
                            <div class="col-3 col-sm-3">
                              <div class="form-group">
                                <input class="form-control"  placeholder="속성명" type="text" :value="attrs[2].trait_type" disabled>
                              </div>
                            </div>
                            <div class="col-3 col-sm-3">
                              <div class="form-group">
                                <input class="form-control"  placeholder="속성값" type="text" :value="attrs[2].value" disabled>
                              </div>
                            </div>
                            <div class="col-3 col-sm-3">
                              <div class="form-group">
                                <input class="form-control"  placeholder="속성명" type="text" :value="attrs[3].trait_type" disabled>
                              </div>
                            </div>
                            <div class="col-3 col-sm-3">
                              <div class="form-group">
                                <input class="form-control"  placeholder="속성값" type="text" :value="attrs[3].value" disabled>
                              </div>
                            </div>
                          </div>
                          <div class="form-row">
                            <div class="col-3 col-sm-3">
                              <div class="form-group">
                                <input class="form-control"  placeholder="속성명" type="text" :value="attrs[4].trait_type" disabled>
                              </div>
                            </div>
                            <div class="col-3 col-sm-3">
                              <div class="form-group">
                                <input class="form-control"  placeholder="속성값" type="text" :value="attrs[4].value" disabled>
                              </div>
                            </div>
                            <div class="col-3 col-sm-3">
                              <div class="form-group">
                                <input class="form-control"  placeholder="속성명" type="text" :value="attrs[5].trait_type" disabled>
                              </div>
                            </div>
                            <div class="col-3 col-sm-3">
                              <div class="form-group">
                                <input class="form-control"  placeholder="속성값" type="text" :value="attrs[5].value" disabled>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </li>
                </ul>
              </div>
            </div>
          </div>
          <div class="col-lg-7 mt-6 mb-6 mt-lg-0">
            <div class="card card-span h-100">
              <div class="card-body pt-3 pb-4">
                <ul class="list-group list-group-flush pt-0 mt-0">
                  <div class="row">
                    <div class="col-2 text-left">
                      <div class="avatar avatar-3xl mt-2">
                        <img class="rounded-circle border border-500 shadow-sm" :src="asset.owner.profileImgUrl" alt="" />
                      </div>
                    </div>
                    <div class="col-10 text-left">
                      <a :href="'/home#/asset/' + asset.id">
                        <h5 class="card-title mb-2 mt-1" v-text="asset.creator.profile"></h5>
                      </a>
                      <p class="fs--1">
                        <a :href="'/home#/assets/' + asset.category">
                          <span class="badge badge-pill badge-secondary mr-1 p-1" v-text="asset.category"></span>
                        </a>, owner by
                        <a href="#!">
                          <span class="text-info font-weight-bold" v-text="asset.owner.profile"></span>
                        </a>
                      </p>
                    </div>
                  </div>
                </ul>
                <ul class="list-group list-group-flush pt-0 mt-0">
                  <li class="list-group-item"></li>
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
                  <li v-if="asset.bidEndDttm != null" class="list-group-item p-4 m-2">
                    <vue-countdown :endDate="asset.bidEndDttm"></vue-countdown>
                  </li>
                  <li v-else class="list-group-item">
                    아직 입찰이 시작되지않았습니다.
                  </li>
                  <li class="list-group-item">
                    <h5 class="mt-2 mb-3 text-left"># 가격 차트 (최근 10건)</h5>
                    <div class="card" style="height: 400px;">
                      <v-chart id="priceChartId" ref="priceChart" autoresize :option="option"/>
                    </div>
                  </li>
                  <li class="list-group-item">
                    <ul class="list-group">
                      <h5 class="mt-2 mb-3 text-left"># 거래내역</h5>
                      <li class="list-group-item d-flex justify-content-between align-items-center" v-for="assetHistory in assetHistories">
                        <div class="d-flex align-items-center">
                          <div class="avatar avatar-3xl mr-2">
                            <img class="rounded-circle border border-500 shadow-sm mr-1" :src="assetHistory.from.profileImgUrl" alt="" />
                          </div>
                          <span class="d-sm-none d-md-block text-info font-weight-bold" v-text="assetHistory.from.profile"></span>
                        </div>
                        <div class="text-right">
                          <div>
                            <span class="far fa-clock"></span>
                            <span v-html="readableDateString(assetHistory.regDttm)"></span>
                          </div>
                          <span v-if="assetHistory.status == 'CREATE'" class="badge badge-soft-primary badge-pill"><span class="text-info" v-text="assetHistory.from.profile"></span>이 NFT 생성하였습니다.</span>
                          <span v-else-if="assetHistory.status == 'SELL'" class="badge badge-soft-primary badge-pill"><span class="text-info" v-text="assetHistory.to.profile"></span>에게 <span class="ml-1 badge badge-soft-danger badge-pill" v-text="assetHistory.price + ' GEM'"></span>으로 NFT 판매하였습니다.</span>
                          <span v-else-if="assetHistory.status == 'DEPOSIT'" class="badge badge-soft-primary badge-pill"><span class="text-info" v-text="assetHistory.from.profile"></span>이 NFT 입금하였습니다</span>
                          <span v-else-if="assetHistory.status == 'WITHDRAW'" class="badge badge-soft-primary badge-pill"><span class="text-info" v-text="assetHistory.from.profile"></span>이 NFT 출금하였습니다</span>
                          <span v-else class="badge badge-soft-primary badge-pill" v-text="assetHistory.status"></span>
                        </div>
                      </li>
                    </ul>
                  </li>
                </ul>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>`
  , components : {
    'notice-alert-view-component' : noticeAlertViewComponent,
    'vue-countdown' : countdownViewComponent,
    'v-chart': VueECharts
  }
  , data : function() {
    return {
      user : _user,
      notice : {},
      asset: {},
      attrs : {},
      assetHistories : {},
      params : this.$route.params,

      option: {
        xAxis: {
          type: 'category',
          data: []
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            data: [],
            type: 'line'
          }
        ]
      }
    }
  }
  , mounted : function() {
    this.onChart();
  }
  , created: function() {
    this.onLoad();
  }
  , methods : {
    onChart : function() {
      assetHistoriesProvider.getAssetHistoriesByAssetId(this, function ($this, result) {

        var dates = [];
        var prices = [];
        result.data.forEach(function(row) {
          if (row.status == 'SELL') {
            prices.push(row.price);
            dates.push(row.regDttm.split('T')[0]);
          }
        });

        $this.option = {
          xAxis: {
            type: 'category',
            data: dates
          },
          yAxis: {
            type: 'value'
          },
          series: [
            {
              data: prices,
              type: 'line'
            }
          ]
        };
      }, this.params.id, 0, 10);
    },
    onLoad : function() {
      assetsProvider.getAssetById(this, function($this, result) {
        $this.asset = result.data;
        $this.attrs = JSON.parse(result.data.attrs);
      }, this.params.id);

      assetHistoriesProvider.getAssetHistoriesByAssetId(this, function ($this, result) {
        $this.assetHistories = result.data.reverse();
      }, this.params.id, 0, 1000);
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