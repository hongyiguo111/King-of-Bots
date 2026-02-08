export default {
    state: {
        is_record: false,
        a_username: "",
        a_photo: "",
        a_steps: [],
        b_username: "",
        b_photo: "",
        b_steps: [],
        record_loser: "",
    },
    getters: {
    },
    mutations: {
        updateIsRecord(state, is_record) {
            state.is_record = is_record;
        },
        updateSteps(state, data) {
            state.a_steps = data.a_steps;
            state.b_steps = data.b_steps;
        },
        updateRecordLoser(state, record_loser) {
            state.record_loser = record_loser;
        },
        updateInfo(state, data) {
            state.a_photo = data.a_photo;
            state.a_username = data.a_username;
            state.b_photo = data.b_photo;
            state.b_username = data.b_username;
        },
    },
    actions: {

    },
    modules: {
    }
};