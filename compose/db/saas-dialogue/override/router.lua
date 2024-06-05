local vshard = require('vshard')
local uuid = require('uuid')
local log = require('log')

function get_dialogue_by_id(dialogue_id)
    local bucket_id = vshard.router.bucket_id_mpcrc32(dialogue_id)
    local dialogue, err = vshard.router.callro(
            bucket_id,
            'get_dialogue_by_id',
            { dialogue_id }
    )
    if err ~= nil then
        log.error(err)
        return nil
    end
    return dialogue
end

function add_dialogue(participants)
    local dialogue_id = uuid.new()
    local bucket_id = vshard.router.bucket_id_mpcrc32(dialogue_id)
    local dialogue_tuple = { bucket_id, dialogue_id, participants }
    local created, err = vshard.router.callrw(
            bucket_id,
            'create_dialogue',
            { dialogue_tuple }
    )

    if err ~= nil then
        log.error(err)
        return nil
    end

    return created
end

function find_all_dialogue_messages(dialogue_id)
    local bucket_id = vshard.router.bucket_id_mpcrc32(dialogue_id)
    local message, err = vshard.router.callro(
            bucket_id,
            'find_all_dialogue_messages',
            { dialogue_id }
    )
    if err ~= nil then
        log.error(err)
        return nil
    end
    return message
end

function add_dialogue_message(dialogue_id, author_id, recipient_id, payload)
    local bucket_id = vshard.router.bucket_id_mpcrc32(dialogue_id)
    local id = uuid.new()
    local dialogue_message_tuple = { bucket_id, id, dialogue_id, author_id, recipient_id, payload }
    log.info(print(dialogue_message_tuple))
    local created, err = vshard.router.callrw(
            bucket_id,
            'create_dialogue_message',
            { dialogue_message_tuple }
    )
    if err ~= nil then
        log.error(err)
        return nil
    end

    return created
end

while true do
    local ok, err = vshard.router.bootstrap({
        if_not_bootstrapped = true,
    })
    if ok then
        break
    end
    log.info(('Router bootstrap error: %s'):format(err))
end





