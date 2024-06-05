local dialogue = {}
local clock = require('clock')

function dialogue.model()
    local model = {}

    model.SPACE_NAME = 'dialogue'
    model.PRIMARY_INDEX = 'primary'
    model.BUCKET_INDEX = 'bucket'
    model.PARTICIPANTS_INDEX = 'participants'

    model.BUCKET_ID = 1
    model.ID = 2
    model.PARTICIPANTS = 3
    model.CREATED_DATE = 4
    model.UPDATED_DATE = 5

    function model.get_space()
        return box.space[model.SPACE_NAME]
    end

    function model.get_by_id(id)
        return model.get_space():select(id)
    end

    function model.create(dialogue_tuple)
        local now = os.date("!%Y-%m-%dT%H:%M:%SZ", clock.time())
        return model.get_space():insert {
            dialogue_tuple[model.BUCKET_ID],
            dialogue_tuple[model.ID],
            dialogue_tuple[model.PARTICIPANTS],
            now,
            now
        }
    end

    return model
end

return dialogue
