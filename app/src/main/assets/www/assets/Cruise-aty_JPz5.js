var l={exports:{}},o={};/**
 * @license React
 * react-jsx-runtime.production.js
 *
 * Copyright (c) Meta Platforms, Inc. and affiliates.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */var d;function p(){if(d)return o;d=1;var e=Symbol.for("react.transitional.element"),n=Symbol.for("react.fragment");function t(x,r,s){var u=null;if(s!==void 0&&(u=""+s),r.key!==void 0&&(u=""+r.key),"key"in r){s={};for(var a in r)a!=="key"&&(s[a]=r[a])}else s=r;return r=s.ref,{$$typeof:e,type:x,key:u,ref:r!==void 0?r:null,props:s}}return o.Fragment=n,o.jsx=t,o.jsxs=t,o}var c;function v(){return c||(c=1,l.exports=p()),l.exports}var i=v();function f({ctx:e}){return i.jsxs("div",{className:"fixed inset-0 flex flex-col overflow-y-auto space-y-20 py-20 ",children:[i.jsx("div",{className:"text-lg text-center",children:"Cruising Story"}),i.jsx("div",{className:"flex flex-col space-y-2 place-content-center place-items-center",children:e.book.chapters.map((n,t)=>i.jsxs("div",{onClick:()=>{e.book.index=t,e.last.index=t,e.locals.set("last",e.last),e.audio.current.currentTime=n.start,history.back()},className:`cursor-pointer ${t==e.book.index?"underline":""}`,children:["Chapter ",t+1," (",n.start," - ",n.end||e.audio.current.duration,")"]},t))})]})}const R=Object.freeze(Object.defineProperty({__proto__:null,default:f},Symbol.toStringTag,{value:"Module"}));export{R as C,i as j};
