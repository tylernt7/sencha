document.addEventListener('DOMContentLoaded', () => {
    const ingatlanokDiv = document.getElementById('ingatlanok');
    const varosFilter = document.getElementById('varos');
    const allapotFilter = document.getElementById('allapot');
    const erkelyFilter = document.getElementById('erkely');
    const butorozvaFilter = document.getElementById('butorozva');

    const isBudapestPage = window.location.pathname.includes('budapest.html');
    const isVidekPage = window.location.pathname.includes('videk.html');

    const filterIngatlanok = () => {
        const varos = varosFilter.value;
        const allapot = allapotFilter.value;
        const erkely = erkelyFilter.value;
        const butorozva = butorozvaFilter.value;

        const filteredIngatlanok = ingatlanok.filter(ingatlan => {
            const isBudapest = ingatlan.varos.includes('Budapest');
            if (isBudapestPage && !isBudapest) return false;
            if (isVidekPage && isBudapest) return false;

            return (varos === 'osszes' || ingatlan.varos === varos) &&
                (allapot === 'osszes' || ingatlan.allapot === allapot) &&
                (erkely === 'osszes' || (erkely === 'van' && ingatlan.erkely) || (erkely === 'nincs' && !ingatlan.erkely)) &&
                (butorozva === 'osszes' || (butorozva === 'igen' && ingatlan.butorozva) || (butorozva === 'nem' && !ingatlan.butorozva));
        });

        ingatlanokDiv.innerHTML = '';
        filteredIngatlanok.forEach(ingatlan => {
            const ingatlanElem = document.createElement('div');
            ingatlanElem.classList.add('ingatlan');
            ingatlanElem.innerHTML = `
<div class="ingatlan-text">
<h3>${ingatlan.cim}</h3>
<p>Város: ${ingatlan.varos}</p>
<p>Belső tér: ${ingatlan.belsoter} m²</p>
<p>Kert: ${ingatlan.kert} m²</p>
<p>Helyiségek: ${ingatlan.helyisegek}</p>
<p>Állapot: ${ingatlan.allapot}</p>
<p>Erkély: ${ingatlan.erkely ? 'Van' : 'Nincs'}</p>
<p>Bútorozva: ${ingatlan.butorozva ? 'Igen' : 'Nem'}</p>
</div>
<div class="ingatlan-image">
<img src="${ingatlan.kep}" alt="${ingatlan.cim}">
</div>
`;
            ingatlanokDiv.appendChild(ingatlanElem);
        });
    };

    varosFilter.addEventListener('change', filterIngatlanok);
    allapotFilter.addEventListener('change', filterIngatlanok);
    erkelyFilter.addEventListener('change', filterIngatlanok);
    butorozvaFilter.addEventListener('change', filterIngatlanok);

    filterIngatlanok();
});