console.info('Hello, World! (from Totemic startup test script)')
// Some event handlers for testing the Totemic Kube events, see also TestEventHandlers.java

TotemicEvents.modifyMusicInstruments(event => {
    console.log('MusicInstrument modification event fired')
    event.modify('totemic:flute', instr => {
        // instr.item = 'minecraft:stick'
        // instr.sound = 'block.dispenser.dispense'
        // instr.baseOutput = 3000
        // instr.musicMaximum = 6000
    })
})

const PotionTotemEffect = Java.loadClass('pokefenn.totemic.api.totem.PotionTotemEffect')
TotemicEvents.modifyTotemCarvings(event => {
    console.log('Totem Carving modification event fired')
    event.modify('totemic:horse', carving => {
        // carving.medicineBagDrain = 1000
        // carving.effects = [new PotionTotemEffect('minecraft:hunger', false, 20)]
    })
    event.modify('totemic:cow', carving => {
        // carving.medicineBagDrain += 120
        // carving.effects[1] = new PotionTotemEffect('minecraft:mining_fatigue')
        // carving.effects.push(new PotionTotemEffect('minecraft:regeneration'))
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