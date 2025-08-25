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