<template>
    <div id="request_loading">
        <div v-show="isShow" class="loader">
            <div class="dot"></div>
            <div class="dot"></div>
            <div class="dot"></div>
            <div class="dot"></div>
            <div class="dot"></div>
        </div>
    </div>
</template>

<script>
    import {bus} from '../bus'
    export default {
        data: function () {
            return {
                isShow: false
            }
        },
        created: function () {
            bus.$on('isShowLoad', (data) => {
                this.isShow = data;
            })
        }
    }
</script>

<style>
    .loader {
        position: absolute;
        z-index:999999;
        top: 50%;
        left: 40%;
        margin-left: 10%;
        transform: translate3d(-50%, -50%, 0);
    }
    .dot {
        width: 24px;
        height: 24px;
        background: #3ac;
        border-radius: 100%;
        display: inline-block;
        animation: slide 1s infinite;
    }
    .dot:nth-child(1) {
        animation-delay: 0.1s;
        background: #32aacc;
    }
    .dot:nth-child(2) {
        animation-delay: 0.2s;
        background: #64aacc;
    }
    .dot:nth-child(3) {
        animation-delay: 0.3s;
        background: #96aacc;
    }
    .dot:nth-child(4) {
        animation-delay: 0.4s;
        background: #c8aacc;
    }
    .dot:nth-child(5) {
        animation-delay: 0.5s;
        background: #faaacc;
    }
    @-moz-keyframes slide {
        0% {
            transform: scale(1);
        }
        50% {
            opacity: 0.3;
            transform: scale(2);
        }
        100% {
            transform: scale(1);
        }
    }
    @-webkit-keyframes slide {
        0% {
            transform: scale(1);
        }
        50% {
            opacity: 0.3;
            transform: scale(2);
        }
        100% {
            transform: scale(1);
        }
    }
    @-o-keyframes slide {
        0% {
            transform: scale(1);
        }
        50% {
            opacity: 0.3;
            transform: scale(2);
        }
        100% {
            transform: scale(1);
        }
    }
    @keyframes slide {
        0% {
            transform: scale(1);
        }
        50% {
            opacity: 0.3;
            transform: scale(2);
        }
        100% {
            transform: scale(1);
        }
    }

</style>

