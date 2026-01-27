<template>
    <div class="matchground">
        <div class="row">
            <div class="col-6">
                <div class="user-photo">
                    <img :src="$store.state.user.photo" alt="">
                </div>
                <div class="user-username">
                    {{ $store.state.user.username }}
                </div>
            </div>
            <div class="col-6">
                <div class="user-photo">
                    <img :src="$store.state.pk.opponent_photo" alt="">
                </div>
                <div class="user-username">
                    {{ $store.state.pk.opponent_username }}
                </div>
            </div>
            <div class="col-12">
                <button @click="click_match_btn" class="btn btn-warning">{{ match_btn_info }}</button>
            </div>
        </div>
    </div>
</template>

<script>
import { ref } from 'vue';
import { useStore } from 'vuex';

export default {
    components: {

    },
    setup() {
        const store = useStore();
        let match_btn_info = ref("开始匹配");

        const click_match_btn = () => {
            if (match_btn_info.value === "开始匹配") {
                match_btn_info.value = "取消匹配";
                store.state.pk.socket.send(JSON.stringify({
                    event: "start-matching",
                    data: {}
                }));
            } else {
                match_btn_info.value = "开始匹配";
                store.state.pk.socket.send(JSON.stringify({
                    event: "stop-matching",
                    data: {}
                }));
            }
        };
        return {
            match_btn_info,
            click_match_btn
        };
    }
}
</script>

<style scoped>
div.matchground {
    width: 60vw;
    height: 70vh;
    margin: 40px auto;
    background: #3f3f74;
    border: 4px solid #fff;
    box-shadow:
        8px 8px 0 #000,
        inset -4px -4px 0 #2a2a50,
        inset 4px 4px 0 #5555a0;
    padding: 40px 20px;
    display: flex;
    align-items: center;
    image-rendering: pixelated;
}

div.matchground .row {
    width: 100%;
    align-items: center;
}

.user-photo {
    text-align: center;
    padding: 10px;
}

.user-photo img {
    width: 100px;
    height: 100px;
    border: 4px solid #fff;
    box-shadow: 4px 4px 0 #000;
    image-rendering: pixelated;
    transition: transform 0.1s;
}

.user-photo img:hover {
    transform: translate(-2px, -2px);
    box-shadow: 6px 6px 0 #000;
}

.user-username {
    text-align: center;
    font-size: 20px;
    font-weight: 700;
    margin-top: 15px;
    color: #ffcd75;
    font-family: 'Courier New', monospace;
    text-shadow: 3px 3px 0 #000;
    letter-spacing: 1px;
}

.col-12 {
    text-align: center;
    margin-top: 50px;
}

.col-12 .btn {
    padding: 14px 48px;
    font-size: 18px;
    background: #5fcde4;
    border: 4px solid #fff;
    box-shadow: 4px 4px 0 #000;
    color: #222034;
    cursor: pointer;
    font-family: 'Courier New', monospace;
    font-weight: 700;
    letter-spacing: 1px;
    transition: transform 0.1s;
    border-radius: 0;
}

.col-12 .btn:hover {
    background: #cbdbfc;
}

.col-12 .btn:active {
    transform: translate(2px, 2px);
    box-shadow: 2px 2px 0 #000;
}

.col-12 .btn-warning {
    background: #ffcd75;
}

.col-12 .btn-warning:hover {
    background: #ffe0a3;
}
</style>