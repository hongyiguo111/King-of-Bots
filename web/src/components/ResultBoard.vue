<template>
    <div class="result-board">
        <div class="result-board-text" v-if="$store.state.pk.loser === 'all'">
            平局！
        </div>
        <div class="result-board-text"
            v-else-if="$store.state.pk.loser === 'A' && $store.state.pk.a_id === parseInt($store.state.user.id)">
            你输了！
        </div>
        <div class="result-board-text"
            v-else-if="$store.state.pk.loser === 'B' && $store.state.pk.b_id === parseInt($store.state.user.id)">
            你输了！
        </div>
        <div class="result-board-text" v-else>
            你赢了！
        </div>
        <div class="result-board-btn">
            <button @click="restart" class="btn btn-warning">再来一局</button>
        </div>
    </div>
</template>


<script>
import { useStore } from 'vuex';
export default {
    setup() {
        const store = useStore();
        const restart = () => {
            store.commit('updateStatus', 'matching');
            store.commit('updateLoser', 'none');
            store.commit('updateOpponent', {
                photo: "https://cdn.acwing.com/media/article/image/2022/08/09/1_1db2488f17-anonymous.png",
                username: "我的对手"
            });
        }
        return {
            restart
        }
    }
}
</script>

<style scoped>
.result-board {
    width: 100%;
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: aqua;
}
</style>