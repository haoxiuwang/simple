var d={exports:{}},o={};/**
 * @license React
 * react-jsx-runtime.production.js
 *
 * Copyright (c) Meta Platforms, Inc. and affiliates.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */var l;function v(){if(l)return o;l=1;var e=Symbol.for("react.transitional.element"),i=Symbol.for("react.fragment");function s(c,r,n){var u=null;if(n!==void 0&&(u=""+n),r.key!==void 0&&(u=""+r.key),"key"in r){n={};for(var x in r)x!=="key"&&(n[x]=r[x])}else n=r;return r=n.ref,{$$typeof:e,type:c,key:u,ref:r!==void 0?r:null,props:n}}return o.Fragment=i,o.jsx=s,o.jsxs=s,o}var a;function j(){return a||(a=1,d.exports=v()),d.exports}var t=j();function p({ctx:e}){return t.jsxs("div",{children:[t.jsx(_,{ctx:e}),t.jsx(R,{ctx:e})]})}function R({ctx:e}){return t.jsx("div",{children:e.book.chapters.map((i,s)=>t.jsxs("div",{children:[s+1,". ",i.title]},s))})}function _({ctx:e}){return t.jsxs("div",{children:[t.jsx("img",{src:e.book.cover_src}),t.jsx("div",{children:book.title})]})}const f=Object.freeze(Object.defineProperty({__proto__:null,default:p},Symbol.toStringTag,{value:"Module"}));export{f as B,t as j};
