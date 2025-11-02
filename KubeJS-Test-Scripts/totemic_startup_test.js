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

TotemicEvents.registerMusicInstruments(event => {
    console.log('Music Instrument registry event fired')
    if(TOTEMIC_DEBUG) {
        event.create('kubejs:test_instrument')
            .baseOutput(200)
            .musicMaximum(3000)
            .displayItem('kubejs:test_instr_item')
            .sound('block.grass.break')
            // .displayItem('invalid:item')
            // .sound('invalid.sound')
            .displayName('Test Music Instrument')
    }
})

TotemicEvents.registerTotemCarvings(event => {
    console.log('Totem Carving registry event fired')
    if(TOTEMIC_DEBUG) {
        event.create('test_carving')
            .potion('totemic:spider')
            .effect(TotemEffect.potion('minecraft:speed', false, 20))
            .medicineBagDrain(1)
            .displayName('Test Carving')
    }
})

TotemicEvents.registerCeremonies(event => {
    console.log('Ceremony registry event fired')
    if(TOTEMIC_DEBUG) {
        event.create('test_ceremony')
            .musicNeeded(2000)
            .maxStartupTime(10 * 20)
            .selectors('kubejs:test_instrument', 'kubejs:test_instrument')
            // .selectors('invalid:instrument', 'kubejs:test_instrument')
            // .selectors()
            // .selectors('totemic:flute')
            // .selectors('totemic:flute', 'totemic:drum', 'kubejs:test_instrument')
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
            // instr.item = 'invalid:item'
            // instr.sound = 'invalid.sound'
            instr.baseOutput += 2000.5
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
            carving.addEffect(TotemEffect.potion('minecraft:mining_fatigue', false))
        })
        event.modify('totemic:cow', carving => {
            carving.medicineBagDrain += 120.5
            carving.addEffect(TotemEffect.potion('minecraft:regeneration'))
            carving.addEffect({
                effect: (level, pos, repetition, context) => {
                    console.log(`effect called for custom TotemEffect (level = ${level}, pos = ${pos}, repetition = ${repetition}, context = ${context})`)
                },
                medicineBagEffect: (player, stack, charge) => {
                    console.log(`medicineBagEffect called for custom TotemEffect (player = ${player}, stack = ${stack}, charge = ${charge})`)
                },
                getInterval: () => 10 * 20, // this doesn't get called, issue with Rhino?
            })

            // carving.addEffect(TotemEffect.potion('invalid:effect'))
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
            ceremony.maxStartupTime *= 1.2042
            ceremony.selectors = ['totemic:wind_chime', 'kubejs:test_instrument']
            // ceremony.selectors = ['totemic:flute', 'totemic:drum']
            // ceremony.selectors = ['invalid:instrument', 'totemic:drum']
            // ceremony.selectors = []
            // ceremony.selectors = ['totemic:flute']
            // ceremony.selectors = ['totemic:flute', 'totemic:drum', 'kubejs:test_instrument']
        }
    })
})