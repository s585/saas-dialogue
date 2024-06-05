xpcall(function()
    require('strict').on()

    local dialogue = require('dialogue'):model()
    local dialogue_message = require('dialogue_message'):model()

    local dialogue_space = box.schema.space.create(
            dialogue.SPACE_NAME, {
                format = {
                    { name = 'bucket_id', type = 'unsigned', is_nullable = false },
                    { name = 'id', type = 'uuid', is_nullable = false },
                    { name = 'participants', type = 'array', is_nullable = false },
                    { name = 'created_date', type = 'string', is_nullable = false },
                    { name = 'updated_date', type = 'string', is_nullable = false }
                },
                if_not_exists = true
            }
    )

    dialogue_space:create_index(
            dialogue.PRIMARY_INDEX,
            { parts = { 'id' },
              type = 'HASH',
              unique = true,
              if_not_exists = true
            }
    )

    dialogue_space:create_index(
            dialogue.BUCKET_INDEX,
            { parts = { 'bucket_id' },
              type = 'TREE',
              unique = false,
              if_not_exists = true
            }
    )

    local dialogue_message_space = box.schema.space.create(
            dialogue_message.SPACE_NAME, {
                format = {
                    { name = 'bucket_id', type = 'unsigned', is_nullable = false },
                    { name = 'id', type = 'uuid', is_nullable = false },
                    { name = 'dialogue_id', type = 'uuid', is_nullable = false },
                    { name = 'author_id', type = 'uuid', is_nullable = false },
                    { name = 'recipient_id', type = 'uuid', is_nullable = false },
                    { name = 'payload', type = 'string', is_nullable = false },
                    { name = 'created_date', type = 'string', is_nullable = false },
                    { name = 'updated_date', type = 'string', is_nullable = false }
                },
                if_not_exists = true
            }
    )

    dialogue_message_space:create_index(
            dialogue_message.PRIMARY_INDEX,
            { parts = { 'id' },
              type = 'HASH',
              unique = true,
              if_not_exists = true
            }
    )

    dialogue_message_space:create_index(
            dialogue_message.BUCKET_INDEX,
            { parts = { 'bucket_id' },
              type = 'TREE',
              unique = false,
              if_not_exists = true
            }
    )

    dialogue_message_space:create_index(
            dialogue_message.DIALOGUE_INDEX,
            { parts = { 'dialogue_id' },
              type = 'TREE',
              unique = false,
              if_not_exists = true
            }
    )

    rawset(_G, 'get_dialogue_by_id',
            function(dialogue_id)
                return dialogue.get_by_id { dialogue_id }
            end)
    box.schema.func.create('get_dialogue_by_id', { if_not_exists = true })
    box.schema.role.grant('public', 'execute', 'function', 'get_dialogue_by_id')

    rawset(_G, 'create_dialogue',
            function(dialogue_tuple)
                return dialogue.create(dialogue_tuple)
            end)
    box.schema.func.create('create_dialogue', { if_not_exists = true })
    box.schema.role.grant('public', 'execute', 'function', 'create_dialogue')

    rawset(_G, 'find_all_dialogue_messages',
            function(dialogue_id)
                return dialogue_message.find_all_by_dialogue_id(dialogue_id)
            end)
    box.schema.func.create('find_all_dialogue_messages', { if_not_exists = true })
    box.schema.role.grant('public', 'execute', 'function', 'find_all_dialogue_messages')

    rawset(_G, 'create_dialogue_message',
            function(dialogue_message_tuple)
                return dialogue_message.create(dialogue_message_tuple)
            end)
    box.schema.func.create('create_dialogue_message', { if_not_exists = true })
    box.schema.role.grant('public', 'execute', 'function', 'create_dialogue_message')

    box.snapshot()

end, function(e)
    print(tostring(e))
    print(debug.traceback())
end)
