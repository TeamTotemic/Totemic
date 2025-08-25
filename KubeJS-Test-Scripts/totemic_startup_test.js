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

TotemicEvents.modifyCeremonies(event => {
    console.log('Ceremony modification event fired')
    event.modify('totemic:rain', ceremony => {
        // ceremony.musicNeeded = 5000
        // ceremony.maxStartupTime = 10 * 20
        // ceremony.selectors = ['totemic:flute', 'totemic:drum']
        // ceremony.selectors = ['totemic:wind_chime', 'totemic:wind_chime']
    })
})