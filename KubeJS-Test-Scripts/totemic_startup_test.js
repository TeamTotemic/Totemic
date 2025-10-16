console.info('Hello, World! (from Totemic startup test script)')
// Some event handlers for testing the Totemic Kube events, see also TestEventHandlers.java

const TOTEMIC_DEBUG = false

// Registry
if(TOTEMIC_DEBUG) {
    StartupEvents.registry('item', event => {
        event.create('kubejs:test_instr_item')
            .use((level, player, hand) => {
                if(player.isShiftKeyDown())
                    TotemicAPI.music().playSelector(player, 'kubejs:test_instrument')
                else
                    TotemicAPI.music().playMusic(player, 'kubejs:test_instrument')
                player.cooldowns.addCooldown('kubejs:test_instr_item', 20)
                return true
            })
    })
}

StartupEvents.registry('totemic:a_instrument', event => {
    console.log('Music Instrument registry event fired')
    if(TOTEMIC_DEBUG) {
        event.create('kubejs:test_instrument')
            .baseOutput(200)
            .musicMaximum(3000)
            .displayItem('kubejs:test_instr_item')
            .sound('block.grass.break')
            .displayName('Test Music Instrument')
    }
})

StartupEvents.registry('totemic:c_totem_carving', event => {
    console.log('Totem Carving registry event fired')
    if(TOTEMIC_DEBUG) {
        event.create('test_carving')
            .potion('totemic:spider')
            .effect(TotemEffect.potion('minecraft:speed', false, 20))
            .medicineBagDrain(1)
            .displayName('Test Carving')
    }
})

StartupEvents.registry('totemic:d_ceremony', event => {
    console.log('Ceremony registry event fired')
    if(TOTEMIC_DEBUG) {
        event.create('test_ceremony')
            .musicNeeded(2000)
            .maxStartupTime(10 * 20)
            .selectors('kubejs:test_instrument', 'kubejs:test_instrument')
            .effect((level, pos, context) => {
                if(context.time % 20 == 0)
                    console.log(`Custom Ceremony effect called (level = ${level}, pos = ${pos}, time = ${context.time})`)
            })
            .effectDuration(5 * 20)
            .displayName('Test Ceremony')
    }
});

// Modification
TotemicEvents.modifyMusicInstruments(event => {
    console.log('MusicInstrument modification event fired')
    event.modify('totemic:flute', instr => {
        if(TOTEMIC_DEBUG) {
            instr.item = 'minecraft:stick'
            instr.sound = 'block.dispenser.dispense'
            instr.baseOutput = 3000
            instr.musicMaximum = 6000
        }
    })
})

TotemicEvents.modifyTotemCarvings(event => {
    console.log('Totem Carving modification event fired')
    if(TOTEMIC_DEBUG) {
        event.modify('totemic:horse', carving => {
            carving.medicineBagDrain = 1000
            carving.effects = [TotemEffect.potion('minecraft:hunger', false, 20)]
        })
        event.modify('totemic:cow', carving => {
            carving.medicineBagDrain += 120
            carving.effects[1] = TotemEffect.potion('minecraft:mining_fatigue', false)
            carving.effects.push(TotemEffect.potion('minecraft:regeneration'))
            carving.effects.push({
                effect: (level, pos, repetition, context) => {
                    console.log(`effect called for custom TotemEffect (level = ${level}, pos = ${pos}, repetition = ${repetition}, context = ${context})`)
                },
                medicineBagEffect: (player, stack, charge) => {
                    console.log(`medicineBagEffect called for custom TotemEffect (player = ${player}, stack = ${stack}, charge = ${charge})`)
                },
                getInterval: () => 10 * 20, // this doesn't get called, issue with Rhino?
            })

            try {
                carving.effects.push(TotemEffect.potion('invalid:effect'))
            }
            catch(e) {
                console.log('Caught ' + e)
            }
        })
        event.modify('totemic:spider', carving => {
            // no access to the effects array
        })
    }
})

TotemicEvents.modifyCeremonies(event => {
    console.log('Ceremony modification event fired')
    event.modify('totemic:rain', ceremony => {
        if(TOTEMIC_DEBUG) {
            ceremony.musicNeeded = 5000
            ceremony.maxStartupTime = 10 * 20
            // ceremony.selectors = ['totemic:flute', 'totemic:drum']
            ceremony.selectors = ['totemic:wind_chime', 'totemic:wind_chime']
        }
    })
})