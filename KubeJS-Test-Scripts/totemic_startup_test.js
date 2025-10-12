console.info('Hello, World! (from Totemic startup test script)')
// Some event handlers for testing the Totemic Kube events, see also TestEventHandlers.java

StartupEvents.registry('totemic:c_totem_carving', event => {
    console.log('Totem Carving registry event fired')
    // event.create('test_carving')
    //     .potion('totemic:spider')
    //     .effect(TotemEffect.potion('minecraft:speed', false, 20))
    //     .medicineBagDrain(1)
    //     .displayName("Test Carving")
})

TotemicEvents.modifyMusicInstruments(event => {
    console.log('MusicInstrument modification event fired')
    event.modify('totemic:flute', instr => {
        // instr.item = 'minecraft:stick'
        // instr.sound = 'block.dispenser.dispense'
        // instr.baseOutput = 3000
        // instr.musicMaximum = 6000
    })
})

TotemicEvents.modifyTotemCarvings(event => {
    console.log('Totem Carving modification event fired')
    event.modify('totemic:horse', carving => {
        // carving.medicineBagDrain = 1000
        // carving.effects = [TotemEffect.potion('minecraft:hunger', false, 20)]
    })
    event.modify('totemic:cow', carving => {
        // carving.medicineBagDrain += 120
        // carving.effects[1] = TotemEffect.potion('minecraft:mining_fatigue', false)
        // carving.effects.push(TotemEffect.potion('minecraft:regeneration'))
        // carving.effects.push(TotemEffect.potion('invalid:effect'))
    })
    event.modify('totemic:spider', carving => {
        // no access to the effects array
    })
})

TotemicEvents.modifyCeremonies(event => {
    console.log('Ceremony modification event fired')
    event.modify('totemic:rain', ceremony => {
        // ceremony.musicNeeded = 5000
        // ceremony.maxStartupTime = 10 * 20
        // ceremony.selectors = ['totemic:flute', 'totemic:drum']
        // ceremony.selectors = ['totemic:wind_chime', 'totemic:wind_chime']
    })
})