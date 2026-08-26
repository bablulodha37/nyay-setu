import{h as n,n as t,l as e,m as a}from"./index-DA95o_go.js";/**
 * @license lucide-react v0.294.0 - ISC
 *
 * This source code is licensed under the ISC license.
 * See the LICENSE file in the root directory of this source tree.
 */const i=n("ArrowUp",[["path",{d:"m5 12 7-7 7 7",key:"hav0vg"}],["path",{d:"M12 19V5",key:"x0mq9r"}]]);function p({className:l=""}){const[s,r]=t.useState(!1);return t.useEffect(()=>{const o=()=>{r(window.scrollY>450)};return o(),window.addEventListener("scroll",o),()=>window.removeEventListener("scroll",o)},[]),s?e.jsx(a.button,{onClick:()=>window.scrollTo({top:0,behavior:"smooth"}),whileHover:{scale:1.05},whileTap:{scale:.95},title:"Scroll to top",className:`scroll-top-button fab fab-assistant-style ${l}`.trim(),"aria-label":"Scroll to top",children:e.jsx(i,{size:26})}):null}export{p as S};
