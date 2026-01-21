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
    background: linear-gradient(135deg, rgba(255, 255, 255, 0.6), rgba(240, 240, 240, 0.4));
    backdrop-filter: blur(10px);
    border-radius: 20px;
    box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
    padding: 40px 20px;
    display: flex;
    align-items: center;
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
    border-radius: 50%;
    object-fit: cover;
    border: 4px solid #fff;
    box-shadow: 0 4px 15px rgba(0, 0, 0, 0.15);
    transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.user-photo img:hover {
    transform: scale(1.08);
    box-shadow: 0 6px 20px rgba(0, 0, 0, 0.2);
}

.user-username {
    text-align: center;
    font-size: 20px;
    font-weight: 600;
    margin-top: 15px;
    color: #333;
    letter-spacing: 0.5px;
}

.col-12 {
    text-align: center;
    margin-top: 50px;
}

.col-12 .btn-primary {
    padding: 12px 40px;
    font-size: 18px;
    border-radius: 30px;
    border: none;
    background: linear-gradient(135deg, #007bff, #0056b3);
    box-shadow: 0 4px 15px rgba(0, 123, 255, 0.4);
    transition: all 0.3s ease;
}

.col-12 .btn-primary:hover {
    transform: translateY(-2px);
    box-shadow: 0 6px 20px rgba(0, 123, 255, 0.5);
}
</style>