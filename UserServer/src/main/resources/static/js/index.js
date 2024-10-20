
let t1 = gsap.timeline({
    scrollTrigger: {
        trigger: '.tl3',
        start: 'top 90%',
        end: 'bottom',
        // toggleActions: 'restart reverse restart reverse',
        toggleActions: 'play pause resume reset',
        autoAlpha: 0,
    },
    ease: 'power4.out',
});

t1.from(".tl3 .tl3_oid", {
    opacity: 0,
    x: '-100%',
    duration: 1,
})
    .from(".tl3 img", {
        x: '100%',
        opacity: 0,
        stagger: 0.1,
        duration: 1,
        ease: 'none',
        rotation: 180,
    }, "<")


let t2 = gsap.timeline({
    scrollTrigger: {
        trigger: '.introduce',
        start: 'top 90%',
        end: 'bottom',
        // toggleActions: 'restart reverse restart reverse',
        toggleActions: 'play pause resume reset',
        autoAlpha: 0,
    },
    ease: 'power4.out',
});

t2.from(".introduce .title", {
    opacity: 0,
    y: '-300%',
    duration: 1,
}, "<")
    .from(".introduce .introduce_item", {
        x: '100%',
        opacity: 0,
        stagger: 0.1,
        duration: 0.5,
        ease: 'none',
    })

let t3 = gsap.timeline({
    scrollTrigger: {
        trigger: '.plan',
        start: 'top 70%',
        end: 'bottom',
        // toggleActions: 'restart reverse restart reverse',
        toggleActions: 'play pause resume reset',
        autoAlpha: 0,
    },
    ease: 'power4.out',
});

t3.from(".plan .plan_title", {
    opacity: 0,
    y: '-300%',
    duration: 1,
})
    .from(".plan .plan_box", {
        x: '-100%',
        opacity: 0,
        duration: 1,
        ease: 'none',
    }, "<")





