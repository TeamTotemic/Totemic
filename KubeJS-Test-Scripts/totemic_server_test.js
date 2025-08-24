console.info('Hello, World! (from Totemic server test script)')
// Some event handlers for testing the Totemic Kube events, see also TestEventHandlers.java

TotemicEvents.ceremonySelection(event => {
    console.log(`CeremonyEvent.Selection fired (selectors = ${event.selectors}, ceremony = ${event.ceremony}, initiator = ${event.initiator})`)
    // event.skipSelectionCheck = true
    // event.cancel()
    // event.ceremony = null
    // event.ceremony = 'totemic:buffalo_dance'
})

TotemicEvents.ceremonySelection('totemic:depths', event => {
    console.log(`CeremonyEvent.Selection fired for Depths`)
})

TotemicEvents.ceremonyStartupTick(event => {
    if(event.context.time % 20 == 0)
        console.log(`CeremonyEvent.StartupTick fired (ceremony = ${event.ceremony}, time = ${event.context.time})`)
    // event.context.startCeremony()
    // event.context.failCeremony()
    // event.cancel()
})

TotemicEvents.ceremonyStartupFail(event => {
    console.log(`CeremonyEvent.StartupFail fired (ceremony = ${event.ceremony})`)
})

TotemicEvents.ceremonyStartupSuccess(event => {
    console.log(`CeremonyEvent.StartupSuccess fired (ceremony = ${event.ceremony})`)
    // event.cancel()
})

TotemicEvents.ceremonyEffectTick(event => {
    if(event.context.time % 20 == 0)
        console.log(`CeremonyEvent.EffectTick fired (ceremony = ${event.ceremony}, time = ${event.context.time})`)
    // event.cancel()
    // if(event.context.time >= 10 * 20) { //will completely end the effect after 10 seconds
    //     event.context.endCeremony()
    // }
})

/* Currently not implemented: */
// TotemicEvents.totemEffect(event => {
//     // console.log(`TotemEffectEvent fired (effect = ${event.effect}, carving = ${event.carving}, rep = ${event.repetition}, pos = ${event.pos})`)
// })

// TotemicEvents.totemEffect('totemic:ocelot', event => {
//     // console.log(`Ocelot effect at ${event.pos} (effect = ${event.effect})`)
//     // event.cancel()
// })

// TotemicEvents.medicineBagEffect(event => {
//     console.log(`MedicineBagEffectEvent fired (player = ${event.player}, effect = ${event.effect}, carving = ${event.carving}, charge = ${event.charge}, deduct = ${event.chargeToDeduct})`)
//     // event.chargeToDeduct = 107
// })