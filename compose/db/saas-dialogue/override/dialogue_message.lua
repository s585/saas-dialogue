local dialogue_message = {}
local clock = require('clock')

function dialogue_message.model()
    local model = {}

    model.SPACE_NAME = 'dialogue_message'
    model.PRIMARY_INDEX = 'primary'
    model.BUCKET_INDEX = 'bucket'
    model.DIALOGUE_INDEX = 'dialogue'

    model.BUCKET_ID = 1
    model.ID = 2
    model.DIALOGUE_ID = 3
    model.AUTHOR_ID = 4
    model.RECIPIENT_ID = 5
    model.PAYLOAD = 6
    model.CREATED_DATE = 7
    model.UPDATED_DATE = 8

    function model.get_space()
        return box.space[model.SPACE_NAME]
    end

    function model.find_all_by_dialogue_id(dialogue_id)
        return model.get_space().index[model.DIALOGUE_INDEX]:select(dialogue_id)
    end

    function model.create(dialogue_message_tuple)
        print(dialogue_message_tuple)
        local now = os.date("!%Y-%m-%dT%H:%M:%SZ", clock.time())
        return model.get_space():insert {
            dialogue_message_tuple[model.BUCKET_ID],
            dialogue_message_tuple[model.ID],
            dialogue_message_tuple[model.DIALOGUE_ID],
            dialogue_message_tuple[model.AUTHOR_ID],
            dialogue_message_tuple[model.RECIPIENT_ID],
            dialogue_message_tuple[model.PAYLOAD],
            now,
            now
        }
    end

    return model
end

return dialogue_message


