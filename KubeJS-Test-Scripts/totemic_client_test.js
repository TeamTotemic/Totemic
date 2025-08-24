console.info('Hello, World! (from Totemic client test script)')
// Some event handlers for testing the Totemic Kube events, see also TestEventHandlers.java

TotemicEvents.ceremonyStartupTick(event => {
    if(event.context.time % 20 == 0)
        console.log(`CeremonyEvent.StartupTick fired (ceremony = ${event.ceremony}, time = ${event.context.time})`)
})

TotemicEvents.ceremonyEffectTick(event => {
    if(event.context.time % 20 == 0)
        console.log(`CeremonyEvent.EffectTick fired (ceremony = ${event.ceremony}, time = ${event.context.time})`)
})

// TotemicEvents.totemEffect(event => {
//     // console.log(`TotemEffectEvent fired (effect = ${event.effect}, carving = ${event.carving}, rep = ${event.repetition}, pos = ${event.pos})`)
// })

// TotemicEvents.totemEffect('totemic:ocelot', event => {
//     // console.log(`Ocelot effect at ${event.pos} (effect = ${event.effect})`)
// })

// TotemicEvents.medicineBagEffect(event => {
//     console.log(`MedicineBagEffectEvent fired (player = ${event.player}, effect = ${event.effect}, carving = ${event.carving}, charge = ${event.charge}, deduct = ${event.chargeToDeduct})`)
// })