<template>
    <div class="result-board">
        <div class="pixel-box">
            <img class="avatar" :src="$store.state.user.photo" alt="">
            <div class="result-text" v-if="$store.state.pk.loser === 'all'">
                DRAW!
            </div>
            <div class="result-text lose" v-else-if="($store.state.pk.loser === 'A' && $store.state.pk.a_id === parseInt($store.state.user.id)) ||
                ($store.state.pk.loser === 'B' && $store.state.pk.b_id === parseInt($store.state.user.id))">
                YOU LOSE!
                <br>
                Rating -2
            </div>
            <div class="result-text win" v-else>
                YOU WIN!
                <br>
                Rating +5
            </div>
            <button @click="restart" class="pixel-btn">再来一局</button>
            <button @click="checkRecord" class="pixel-btn">查看回放</button>
            <button @click="checkRankList" class="pixel-btn">查看排名</button>
        </div>
    </div>
</template>

<script>
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';

export default {
    setup() {
        const store = useStore();
        const router = useRouter();
        const restart = () => {
            store.commit('updateStatus', 'matching');
            store.commit('updateLoser', 'none');
            store.commit('updateOpponent', {
                photo: "https://cdn.acwing.com/media/article/image/2022/08/09/1_1db2488f17-anonymous.png",
                username: "我的对手"
            });
        };

        const checkRecord = () => {
            router.push('/record/');
        };

        const checkRankList = () => {
            router.push('/ranklist/');
        };


        return {
            restart,
            checkRecord,
            checkRankList
        }
    }
}
</script>

<style scoped>
.result-board {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
    background: rgba(34, 32, 52, 0.92);
}

.pixel-box {
    background: #3f3f74;
    border: 4px solid #fff;
    box-shadow:
        8px 8px 0 #000,
        inset -4px -4px 0 #2a2a50,
        inset 4px 4px 0 #5555a0;
    padding: 40px 60px;
    text-align: center;
    image-rendering: pixelated;
}

.avatar {
    width: 80px;
    height: 80px;
    border: 4px solid #fff;
    box-shadow: 4px 4px 0 #000;
    margin-bottom: 20px;
    image-rendering: pixelated;
}

.result-text {
    font-size: 36px;
    font-weight: 700;
    color: #ffcd75;
    font-family: 'Courier New', monospace;
    text-shadow: 4px 4px 0 #000;
    letter-spacing: 2px;
    margin-bottom: 8px;
}

.result-text.win {
    color: #99e550;
}

.result-text.lose {
    color: #d95763;
}

.pixel-btn {
    margin-top: 24px;
    padding: 12px 32px;
    font-size: 16px;
    background: #5fcde4;
    border: 4px solid #fff;
    box-shadow: 4px 4px 0 #000;
    color: #222034;
    cursor: pointer;
    font-family: 'Courier New', monospace;
    font-weight: 700;
    letter-spacing: 1px;
    transition: transform 0.1s;
}

.pixel-btn:hover {
    background: #cbdbfc;
}

.pixel-btn:active {
    transform: translate(2px, 2px);
    box-shadow: 2px 2px 0 #000;
}
</style>