<template>
    <div class="matchground">
        <div class="row">
            <div class="col-4">
                <div class="user-photo">
                    <img :src="$store.state.user.photo" alt="">
                </div>
                <div class="user-username">
                    {{ $store.state.user.username }}
                </div>
            </div>
            <div class="col-4">
                <div class="user-select-bot">
                    <select v-model="select_bot" class="form-select" aria-label="Default select example">
                        <option value="-1" selected>亲自上阵</option>
                        <option v-for="bot in bots" :key="bot.id" :value="bot.id">{{ bot.title }}</option>
                    </select>
                </div>
            </div>
            <div class="col-4">
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
import $ from 'jquery';

export default {
    components: {

    },
    setup() {
        const store = useStore();
        let match_btn_info = ref("开始匹配");
        let bots = ref([]);
        let select_bot = ref(-1);

        const click_match_btn = () => {
            if (match_btn_info.value === "开始匹配") {
                match_btn_info.value = "取消匹配";
                console.log(select_bot.value);
                store.state.pk.socket.send(JSON.stringify({
                    event: "start-matching",
                    bot_id: select_bot.value
                }));
            } else {
                match_btn_info.value = "开始匹配";
                store.state.pk.socket.send(JSON.stringify({
                    event: "stop-matching",
                    data: {}
                }));
            }
        };

        const refresh_bots = () => {
            $.ajax({
                url: "http://127.0.0.1:3000/user/bot/getlist/",
                type: "get",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    bots.value = resp;
                },
                error(resp) {
                    console.log(resp);
                },
            });
        };

        refresh_bots(); // 从云端获取 bot 列表

        return {
            match_btn_info,
            click_match_btn,
            refresh_bots,
            bots, // 暴露 bots 变量，必须得写
            select_bot
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

.user-select-bot {
    text-align: center;
    padding: 20px;
}

.user-select-bot .form-select {
    width: 80%;
    margin: 0 auto;
    padding: 12px 16px;
    font-size: 16px;
    background: #5555a0;
    border: 4px solid #fff;
    box-shadow: 4px 4px 0 #000;
    color: #ffcd75;
    cursor: pointer;
    font-family: 'Courier New', monospace;
    font-weight: 700;
    letter-spacing: 1px;
    border-radius: 0;
    appearance: none;
    -webkit-appearance: none;
    -moz-appearance: none;
    background-image: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='12' height='12' viewBox='0 0 12 12'%3E%3Cpath fill='%23ffcd75' d='M2 4l4 4 4-4z'/%3E%3C/svg%3E");
    background-repeat: no-repeat;
    background-position: right 12px center;
    transition: transform 0.1s;
}

.user-select-bot .form-select:hover {
    background-color: #6666b0;
}

.user-select-bot .form-select:focus {
    outline: none;
    box-shadow: 6px 6px 0 #000;
    transform: translate(-2px, -2px);
}

.user-select-bot .form-select option {
    background: #3f3f74;
    color: #ffcd75;
    padding: 10px;
}
</style>