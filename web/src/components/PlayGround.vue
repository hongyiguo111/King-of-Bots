<template>
    <div class="playground">
        <div class="row" v-if="!$store.state.record.is_record">
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
        </div>
        <div class="row" v-else>
            <div class="col-6">
                <div class="user-photo">
                    <img :src="$store.state.record.a_photo" alt="">
                </div>
                <div class="user-username">
                    {{ $store.state.record.a_username }}
                </div>
            </div>
            <div class="col-6">
                <div class="user-photo">
                    <img :src="$store.state.record.b_photo" alt="">
                </div>
                <div class="user-username">
                    {{ $store.state.record.b_username }}
                </div>
            </div>
        </div>
        <GameMap></GameMap>
    </div>
</template>

<script>
import GameMap from './GameMap.vue';

export default {
    components: {
        GameMap
    }
}
</script>

<style scoped>
div.playground {
    width: 60vw;
    height: 70vh;
    margin: 40px auto;
}

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