export default {
    state: {
        status: "matching", // matching, playing
        socket: null, // 前端和后端建立的链接
        opponent_username: "", // 对手的用户名
        opponent_photo: "", // 对手的头像
        gamemap: null // 游戏地图
    },
    getters: {
    },
    mutations: {
        updateSocket(state, socket) {
            state.socket = socket;
        },
        updateOpponent(state, opponent) {
            state.opponent_username = opponent.username;
            state.opponent_photo = opponent.photo;
        },
        updateStatus(state, status) {
            state.status = status;
        },
        updateGamemap(state, gamemap) {
            state.gamemap = gamemap;
        }
    },
    actions: {

    },
    modules: {
    }
};