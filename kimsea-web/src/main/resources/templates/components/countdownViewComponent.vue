<script type="text/javascript">
const countdownViewComponent = {
    template: `<div>
    <div v-if="isEnded" class="d-flex text-danger text-sans-serif justify-content-center">
        <span class="mr-1 font-weight-bold">입찰종료</span>
    </div>
    <div v-else class="d-flex text-info text-sans-serif justify-content-center">
        <span class="mr-1 font-weight-bold">{{ hours }}시간</span>
        <span class="mr-1 font-weight-bold">{{ minutes }}분</span>
        <span class="font-weight-bold">{{ seconds }}초</span>
    </div>
</div>`,
    props: {
        endDate: {
          type: Date.parse(this.endDate)
      }
},
    data () {
        return {
            days: 0,
            hours: 0,
            minutes: 0,
            seconds: 0,
            isEnded: false
            }
    },
    methods: {
        updateRemaining (distance) {
            this.days = Math.floor(distance / (1000 * 60 * 60 * 24));
            this.hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
            this.minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
            this.seconds = Math.floor((distance % (1000 * 60)) / 1000);
        },
        tick () {
            const currentTime = Math.floor(new Date().getTime());
            const _endDate =  Math.floor(new Date(this.endDate).getTime());
            const distance = Math.max(_endDate - currentTime, 0);

            this.updateRemaining(distance);

            if (distance === 0) {
                clearInterval(this.timer);
                this.isEnded = true;
            }
        }
    },

    mounted () {
        this.tick();
        this.timer = setInterval(this.tick.bind(this), 1000);
    },
    destroy () {
        clearInterval(this.timer);
    }
};
</script>