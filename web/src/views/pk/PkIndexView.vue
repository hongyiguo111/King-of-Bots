<template>
    <PlayGround v-if="$store.state.pk.status === 'playing'">对战</PlayGround>
    <MatchGround v-if="$store.state.pk.status === 'matching'">匹配</MatchGround>
    <ResultBoard v-if="$store.state.pk.loser !== 'none'">结果</ResultBoard>
</template>

<script>
import PlayGround from '@/components/PlayGround.vue';
import MatchGround from '@/components/MatchGround.vue';
import ResultBoard from '@/components/ResultBoard.vue';
import { onMounted, onUnmounted } from 'vue'; // 什么是挂载和卸载？
import { useStore } from 'vuex';
export default {
    components: {
        PlayGround,
        MatchGround,
        ResultBoard
    },
    setup() {
        const store = useStore();
        const socketUrl = `ws://127.0.0.1:3000/websocket/${store.state.user.token}`;

        let socket = null;
        onMounted(() => {
            store.commit('updateOpponent', {
                photo: "https://cdn.acwing.com/media/article/image/2022/08/09/1_1db2488f17-anonymous.png",
                username: "我的对手" // 大括号里的内容作为oppenent传入
            });
            socket = new WebSocket(socketUrl); // WebSocket是js自带的类
            // 这里的onopen、onmessage、onclose是前端js里WebSocket的事件处理函数

            socket.onopen = () => {
                console.log('connected');
                store.commit('updateSocket', socket);
            };

            socket.onmessage = (msg) => {
                const data = JSON.parse(msg.data);
                if (data.event === "start-matching") {
                    store.commit('updateOpponent', {
                        username: data.opponent_username,
                        photo: data.opponent_photo
                    });
                    setTimeout(() => {
                        store.commit('updateStatus', 'playing');
                    }, 200);
                    store.commit('updateGame', data.game);
                } else if (data.event === "move") {
                    console.log(data);
                    const game = store.state.pk.gameObject;
                    const [snake0, snake1] = game.snakes;
                    snake0.set_direction(data.a_direction);
                    snake1.set_direction(data.b_direction);
                } else if (data.event === "result") {
                    console.log(data);
                    const game = store.state.pk.gameObject;
                    const [snake0, snake1] = game.snakes;

                    if (data.loser === "all" || data.loser === "A") {
                        snake0.status = "die";
                    }
                    if (data.loser === "all" || data.loser === "B") {
                        snake1.status = "die";
                    }
                    store.commit('updateLoser', data.loser);
                }
            };

            socket.onclose = () => {
                console.log('disconnected');
            };
        });

        onUnmounted(() => {
            store.commit('updateStatus', 'matching');
            socket.close();
        });
    }
}
</script>

<style scoped></style>