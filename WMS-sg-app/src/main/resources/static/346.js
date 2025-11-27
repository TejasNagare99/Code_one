"use strict";
(self["webpackChunkstk_cfa_angular"] = self["webpackChunkstk_cfa_angular"] || []).push([[346],{

/***/ 74022:
/*!*******************************************************************************!*\
  !*** ./src/app/components/transaction/stktrf-entry/stktrf-entry.component.ts ***!
  \*******************************************************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   StktrfEntryComponent: () => (/* binding */ StktrfEntryComponent),
/* harmony export */   StockTrfDetailComponent: () => (/* binding */ StockTrfDetailComponent)
/* harmony export */ });
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! @angular/forms */ 34456);
/* harmony import */ var src_app_classes_constants__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! src/app/classes/constants */ 5556);
/* harmony import */ var _angular_material_table__WEBPACK_IMPORTED_MODULE_8__ = __webpack_require__(/*! @angular/material/table */ 77697);
/* harmony import */ var _angular_material_paginator__WEBPACK_IMPORTED_MODULE_11__ = __webpack_require__(/*! @angular/material/paginator */ 24624);
/* harmony import */ var _angular_material_dialog__WEBPACK_IMPORTED_MODULE_9__ = __webpack_require__(/*! @angular/material/dialog */ 12587);
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! @angular/core */ 37580);
/* harmony import */ var _services_user_user_service__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ../../../services/user/user.service */ 83273);
/* harmony import */ var src_app_services_stk_trf_stktrf_service__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! src/app/services/stk-trf/stktrf.service */ 12644);
/* harmony import */ var src_app_classes_medico_utility__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! src/app/classes/medico-utility */ 79955);
/* harmony import */ var src_app_services_medico_medico_service__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! src/app/services/medico/medico.service */ 353);
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_10__ = __webpack_require__(/*! @angular/router */ 95072);
/* harmony import */ var _angular_common__WEBPACK_IMPORTED_MODULE_12__ = __webpack_require__(/*! @angular/common */ 60316);
/* harmony import */ var _angular_material_form_field__WEBPACK_IMPORTED_MODULE_13__ = __webpack_require__(/*! @angular/material/form-field */ 24950);
/* harmony import */ var _angular_material_button__WEBPACK_IMPORTED_MODULE_14__ = __webpack_require__(/*! @angular/material/button */ 84175);
/* harmony import */ var _angular_flex_layout_flex__WEBPACK_IMPORTED_MODULE_15__ = __webpack_require__(/*! @angular/flex-layout/flex */ 91447);
/* harmony import */ var _angular_flex_layout_extended__WEBPACK_IMPORTED_MODULE_16__ = __webpack_require__(/*! @angular/flex-layout/extended */ 52913);
/* harmony import */ var _angular_material_input__WEBPACK_IMPORTED_MODULE_17__ = __webpack_require__(/*! @angular/material/input */ 95541);
/* harmony import */ var _angular_material_select__WEBPACK_IMPORTED_MODULE_18__ = __webpack_require__(/*! @angular/material/select */ 25175);
/* harmony import */ var _angular_material_core__WEBPACK_IMPORTED_MODULE_19__ = __webpack_require__(/*! @angular/material/core */ 74646);
/* harmony import */ var _angular_material_tooltip__WEBPACK_IMPORTED_MODULE_20__ = __webpack_require__(/*! @angular/material/tooltip */ 80640);
/* harmony import */ var _directives_utility_numbers_only_directive__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! ../../../directives/utility/numbers-only.directive */ 22128);

























const _c0 = ["prodSelection"];
const _c1 = ["recvloc"];
const _c2 = (a0, a1) => [a0, a1];
const _c3 = () => ({
  standalone: true
});
function StktrfEntryComponent_mat_option_9_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "mat-option", 58);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const list_r2 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("value", list_r2.key);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtextInterpolate"](list_r2.value);
  }
}
function StktrfEntryComponent_mat_option_19_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "mat-option", 58);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const l_r3 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("value", l_r3.loc_id);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtextInterpolate"](l_r3.loc_nm);
  }
}
function StktrfEntryComponent_mat_option_37_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "mat-option", 58);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const list_r4 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("value", list_r4.key);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtextInterpolate"](list_r4.value);
  }
}
function StktrfEntryComponent_mat_option_48_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "mat-option", 58);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const s_r5 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("value", _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵpureFunction2"](2, _c2, s_r5.sgprmdet_nm, s_r5.sg_prmdet_id));
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtextInterpolate"](s_r5.sgprmdet_disp_nm);
  }
}
function StktrfEntryComponent_mat_option_54_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "mat-option", 59);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const list_r6 = ctx.$implicit;
    const ctx_r6 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵnextContext"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵpropertyInterpolate"]("matTooltip", list_r6.prodName);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("value", list_r6.prodId)("disabled", list_r6.prodId !== ctx_r6.enabledProdId && ctx_r6.prodDisabled);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtextInterpolate"](list_r6.prodName);
  }
}
function StktrfEntryComponent_th_83_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "th", 60);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](1, "Batch No");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
  }
}
function StktrfEntryComponent_td_84_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "td", 61);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const row_r8 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtextInterpolate"](row_r8.batch_no);
  }
}
function StktrfEntryComponent_th_86_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "th", 62);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](1, "Mfg Date");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
  }
}
function StktrfEntryComponent_td_87_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "td", 63);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const row_r9 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtextInterpolate"](row_r9.mfg_date);
  }
}
function StktrfEntryComponent_th_89_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "th", 62);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](1, "Expiry Date");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
  }
}
function StktrfEntryComponent_td_90_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "td", 63);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const row_r10 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtextInterpolate"](row_r10.expiry_date);
  }
}
function StktrfEntryComponent_th_92_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "th", 62);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](1, "Rate");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
  }
}
function StktrfEntryComponent_td_93_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "td", 63);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const row_r11 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtextInterpolate"](row_r11.trfRate);
  }
}
function StktrfEntryComponent_th_95_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "th", 62);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](1, "(IGST / SGST+CGST)");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
  }
}
function StktrfEntryComponent_td_96_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "td", 63);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const row_r12 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtextInterpolate1"]("", row_r12.taxRate, " %");
  }
}
function StktrfEntryComponent_th_98_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "th", 62);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](1, "Stock");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
  }
}
function StktrfEntryComponent_td_99_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "td", 63);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const row_r13 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtextInterpolate1"](" ", row_r13.stock, "");
  }
}
function StktrfEntryComponent_th_101_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "th", 62);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](1, "Transfer Qty");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
  }
}
function StktrfEntryComponent_td_102_Template(rf, ctx) {
  if (rf & 1) {
    const _r14 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵgetCurrentView"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "td", 63)(1, "input", 64);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtwoWayListener"]("ngModelChange", function StktrfEntryComponent_td_102_Template_input_ngModelChange_1_listener($event) {
      const i_r15 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵrestoreView"](_r14).index;
      const ctx_r6 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵnextContext"]();
      _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtwoWayBindingSet"](ctx_r6.batchBilledQtys[i_r15], $event) || (ctx_r6.batchBilledQtys[i_r15] = $event);
      return _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵresetView"]($event);
    });
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵlistener"]("change", function StktrfEntryComponent_td_102_Template_input_change_1_listener($event) {
      const i_r15 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵrestoreView"](_r14).index;
      const ctx_r6 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵnextContext"]();
      return _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵresetView"](ctx_r6.checkBilledQuantity($event, i_r15));
    });
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    const i_r15 = ctx.index;
    const ctx_r6 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵnextContext"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtwoWayProperty"]("ngModel", ctx_r6.batchBilledQtys[i_r15]);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("ngModelOptions", _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵpureFunction0"](2, _c3));
  }
}
function StktrfEntryComponent_th_104_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "th", 62);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](1, "Value");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
  }
}
function StktrfEntryComponent_td_105_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "td", 63);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const i_r16 = ctx.index;
    const ctx_r6 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵnextContext"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtextInterpolate1"](" ", ctx_r6.batchValues[i_r16], " ");
  }
}
function StktrfEntryComponent_tr_106_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelement"](0, "tr", 65);
  }
}
function StktrfEntryComponent_tr_107_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelement"](0, "tr", 66);
  }
}
function StockTrfDetailComponent_tr_19_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "tr")(1, "td");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](3, "td");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](4);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](5, "td");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](6);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](7, "td", 6);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](8);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](9, "td", 6);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](10);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](11, "td", 6);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](12);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    const p_r1 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtextInterpolate"](p_r1.stockTrfNo);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtextInterpolate"](p_r1.productName);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtextInterpolate"](p_r1.batchNo);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtextInterpolate"](p_r1.soldQty);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtextInterpolate"](p_r1.rate);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtextInterpolate"]((p_r1.rate * p_r1.soldQty).toFixed(2));
  }
}
let StktrfEntryComponent = /*#__PURE__*/(() => {
  class StktrfEntryComponent {
    userService;
    stktrfService;
    medicoUtility;
    medicoService;
    fb;
    dialog;
    el;
    router;
    session;
    constants = new src_app_classes_constants__WEBPACK_IMPORTED_MODULE_0__.Constants();
    prodSelection;
    recvloc;
    recLocationList = null;
    nilGstOptionsList = new Array();
    tranTypeList = new Array();
    prodList = null;
    isSaleable = true;
    stockTypes = null;
    batchList;
    prodDisabled = false;
    enabledProdId;
    selectedStockType = new Array();
    batchIds = new Array();
    batchTrfRates = new Array();
    batchStocks = new Array();
    batchBilledQtys = new Array();
    batchFreeQtys = new Array();
    batchValues = new Array();
    headerFreezed = false;
    tranTypeTemp;
    recLocationTemp;
    nilGstIndTemp;
    companycode;
    companyFlag;
    dispatchParameters = null;
    paginator;
    // displayedColumns: string[] = ["BatchNo","ExpiryDate","Rate","Stock","BilledQty","FreeQty","Value"];
    displayedColumns = ["BatchNo", "MfgDate", "ExpiryDate", "Rate", "TaxRate", "Stock", "BilledQty", "Value"];
    form;
    ngOnInit() {}
    constructor(userService, stktrfService, medicoUtility, medicoService, fb, dialog, el, router) {
      this.userService = userService;
      this.stktrfService = stktrfService;
      this.medicoUtility = medicoUtility;
      this.medicoService = medicoService;
      this.fb = fb;
      this.dialog = dialog;
      this.el = el;
      this.router = router;
      this.form = this.fb.group({
        saveMode: new _angular_forms__WEBPACK_IMPORTED_MODULE_7__.FormControl("", _angular_forms__WEBPACK_IMPORTED_MODULE_7__.Validators.required),
        headerId: new _angular_forms__WEBPACK_IMPORTED_MODULE_7__.FormControl(0),
        sendSubCompId: new _angular_forms__WEBPACK_IMPORTED_MODULE_7__.FormControl(""),
        sendLocId: new _angular_forms__WEBPACK_IMPORTED_MODULE_7__.FormControl(""),
        sendLocStateId: new _angular_forms__WEBPACK_IMPORTED_MODULE_7__.FormControl(""),
        stockTypeDetId: new _angular_forms__WEBPACK_IMPORTED_MODULE_7__.FormControl(""),
        tranType: new _angular_forms__WEBPACK_IMPORTED_MODULE_7__.FormControl("", _angular_forms__WEBPACK_IMPORTED_MODULE_7__.Validators.required),
        sendLocation: new _angular_forms__WEBPACK_IMPORTED_MODULE_7__.FormControl("", _angular_forms__WEBPACK_IMPORTED_MODULE_7__.Validators.required),
        recLocation: new _angular_forms__WEBPACK_IMPORTED_MODULE_7__.FormControl("", _angular_forms__WEBPACK_IMPORTED_MODULE_7__.Validators.required),
        trfType: new _angular_forms__WEBPACK_IMPORTED_MODULE_7__.FormControl("", _angular_forms__WEBPACK_IMPORTED_MODULE_7__.Validators.required),
        stktrfNo: new _angular_forms__WEBPACK_IMPORTED_MODULE_7__.FormControl(""),
        trfDate: new _angular_forms__WEBPACK_IMPORTED_MODULE_7__.FormControl("", _angular_forms__WEBPACK_IMPORTED_MODULE_7__.Validators.required),
        nilGstInd: new _angular_forms__WEBPACK_IMPORTED_MODULE_7__.FormControl("", _angular_forms__WEBPACK_IMPORTED_MODULE_7__.Validators.required),
        productId: new _angular_forms__WEBPACK_IMPORTED_MODULE_7__.FormControl("", _angular_forms__WEBPACK_IMPORTED_MODULE_7__.Validators.required),
        stockTypeId: new _angular_forms__WEBPACK_IMPORTED_MODULE_7__.FormControl(""),
        billedQty: new _angular_forms__WEBPACK_IMPORTED_MODULE_7__.FormControl("", _angular_forms__WEBPACK_IMPORTED_MODULE_7__.Validators.required),
        freeQty: new _angular_forms__WEBPACK_IMPORTED_MODULE_7__.FormControl(""),
        totalStock: new _angular_forms__WEBPACK_IMPORTED_MODULE_7__.FormControl(""),
        wthStkQty: new _angular_forms__WEBPACK_IMPORTED_MODULE_7__.FormControl(""),
        billableStk: new _angular_forms__WEBPACK_IMPORTED_MODULE_7__.FormControl(""),
        billedStkValue: new _angular_forms__WEBPACK_IMPORTED_MODULE_7__.FormControl(""),
        totalBilledQty: new _angular_forms__WEBPACK_IMPORTED_MODULE_7__.FormControl(""),
        totalFreeQty: new _angular_forms__WEBPACK_IMPORTED_MODULE_7__.FormControl(""),
        totalStkTrfvalue: new _angular_forms__WEBPACK_IMPORTED_MODULE_7__.FormControl(""),
        batchIds: new _angular_forms__WEBPACK_IMPORTED_MODULE_7__.FormControl(""),
        batchBilledQtys: new _angular_forms__WEBPACK_IMPORTED_MODULE_7__.FormControl(""),
        batchFreeQtys: new _angular_forms__WEBPACK_IMPORTED_MODULE_7__.FormControl(""),
        batchTrfRates: new _angular_forms__WEBPACK_IMPORTED_MODULE_7__.FormControl(""),
        currPeriodCode: new _angular_forms__WEBPACK_IMPORTED_MODULE_7__.FormControl(""),
        currFinYear: new _angular_forms__WEBPACK_IMPORTED_MODULE_7__.FormControl(""),
        empId: new _angular_forms__WEBPACK_IMPORTED_MODULE_7__.FormControl(""),
        gstInd: new _angular_forms__WEBPACK_IMPORTED_MODULE_7__.FormControl("")
      });
      // getting company code
      userService.getcompanydata().subscribe(res => {
        this.companycode = res.company.company;
        if (this.companycode.trim() == 'PAL') {
          //console.log(this.companycode);
          this.companyFlag = true;
          //console.log("inside getcompdata fn"+this.companycode);
        }
      });
      this.medicoService.headingName = "Stock Transfer Entry";
      this.form.get("saveMode")?.setValue(this.constants.ENTRY_MODE.toString());
      this.medicoUtility.toggleProgressBar(true);
      this.session = this.medicoUtility.getSessionVariables();
      this.medicoUtility.toggleProgressBar(false);
      this.nilGstOptionsList = [{
        key: "N",
        value: "No"
      }, {
        key: "Y",
        value: "Yes"
      }];
      this.stktrfService.getDataForStkTrfEntry(this.session.EMP_ID).subscribe(response => {
        this.form.get("sendLocation")?.setValue(response.sendLocName);
        this.form.get("sendSubCompId")?.setValue(response.sendSubCompId);
        this.form.get("sendLocId")?.setValue(response.sendLocId);
        this.form.get("sendLocStateId")?.setValue(response.sendLocStateId);
        this.recLocationList = response.recLocations;
        this.dispatchParameters = response.dispatchParametersList;
        this.form.get("trfType")?.setValue(response.trfType);
        this.form.get("trfDate")?.setValue(response.stkTrfDate);
        this.medicoUtility.toggleProgressBar(false);
        if (this.dispatchParameters) this.tranTypeList = [{
          key: "85",
          value: this.dispatchParameters[0].sgprmdet_disp_nm
        }, {
          key: "86",
          value: this.dispatchParameters[1].sgprmdet_disp_nm
        }];
        this.form.get("nilGstInd")?.setValue(this.nilGstOptionsList[0].key);
        this.form.get("tranType")?.setValue(this.tranTypeList[0].key);
      });
      // this.tranTypeList = [ { key: "85", value: "Saleable"},{ key: "86", value: "Non Saleable"}];
      // this.form.get("nilGstInd")?.setValue(this.nilGstOptionsList[0].key);
      // this.form.get("tranType")?.setValue(this.tranTypeList[0].key);
      // this.getDataForStkTrfEntry();
    }
    scroll(el) {
      el.scrollIntoView({
        behavior: 'smooth'
      });
    }
    getDataForStkTrfEntry() {
      this.medicoUtility.toggleProgressBar(true);
      this.stktrfService.getDataForStkTrfEntry(this.session.EMP_ID).subscribe(response => {
        this.form.get("sendLocation")?.setValue(response.sendLocName);
        this.form.get("sendSubCompId")?.setValue(response.sendSubCompId);
        this.form.get("sendLocId")?.setValue(response.sendLocId);
        this.form.get("sendLocStateId")?.setValue(response.sendLocStateId);
        this.recLocationList = response.recLocations;
        this.form.get("trfType")?.setValue(response.trfType);
        this.form.get("trfDate")?.setValue(response.stkTrfDate);
        this.medicoUtility.toggleProgressBar(false);
      });
    }
    getProductList() {
      if (this.form.get("nilGstInd")?.valid && this.form.get("recLocation")?.valid && this.form.get("tranType")?.valid) {
        if (this.form.get("tranType")?.value === '85') {
          this.medicoUtility.toggleProgressBar(true);
          this.batchList = new _angular_material_table__WEBPACK_IMPORTED_MODULE_8__.MatTableDataSource();
          this.prodList = new Array();
          this.form.get("wthStkQty")?.reset();
          this.form.get("billableStk")?.reset();
          this.form.get("totalStock")?.reset();
          this.form.get('totalBilledQty')?.reset();
          this.form.get('totalFreeQty')?.reset();
          this.form.get('totalStkTrfvalue')?.reset();
          this.prodDisabled = false;
          this.enabledProdId = '';
          this.stktrfService.getProductListBySendRecLoc(this.form.get("sendLocId")?.value, this.form.get("sendSubCompId")?.value, this.form.get("sendLocStateId")?.value, this.form.get("nilGstInd")?.value, '').subscribe(response => {
            this.prodList = response.prodList.splice(0);
            this.form.get('productId')?.reset();
            this.medicoUtility.toggleProgressBar(false);
          });
        } else {
          if (this.form.get("stockTypeId")?.valid && this.form.get("stockTypeId")?.value !== '') {
            this.medicoUtility.toggleProgressBar(true);
            this.batchList = new _angular_material_table__WEBPACK_IMPORTED_MODULE_8__.MatTableDataSource();
            this.prodList = new Array();
            this.form.get("wthStkQty")?.reset();
            this.form.get("billableStk")?.reset();
            this.form.get("totalStock")?.reset();
            this.form.get('totalBilledQty')?.reset();
            this.form.get('totalFreeQty')?.reset();
            this.form.get('totalStkTrfvalue')?.reset();
            this.prodDisabled = false;
            this.enabledProdId = '';
            this.selectedStockType = this.form.get("stockTypeId")?.value;
            this.stktrfService.getProductListBySendRecLoc(this.form.get("sendLocId")?.value, this.form.get("sendSubCompId")?.value, this.form.get("sendLocStateId")?.value, this.form.get("nilGstInd")?.value, this.selectedStockType[0]).subscribe(response => {
              this.prodList = response.prodList.splice(0);
              this.form.get('productId')?.reset();
              this.medicoUtility.toggleProgressBar(false);
            });
          }
        }
      }
    }
    getStockTypes() {
      if (this.form.get("tranType")?.valid && this.form.get("tranType")?.value == '86') {
        this.isSaleable = false;
        this.medicoUtility.toggleProgressBar(true);
        this.stktrfService.getStockTypes().subscribe(response => {
          this.stockTypes = response.stockTypes.splice(0);
          this.medicoUtility.toggleProgressBar(false);
        });
        this.getProductList();
        this.medicoUtility.toggleProgressBar(false);
      } else {
        this.isSaleable = true;
        this.getProductList();
      }
    }
    getBatchDetails() {
      this.medicoUtility.toggleProgressBar(true);
      if (this.form.get("tranType")?.valid && this.form.get("productId")?.valid) {
        this.stktrfService.setProdLock(this.form.get("productId")?.value, this.form.get("sendLocId")?.value, this.session.EMP_ID).subscribe(response => {
          if (response) {
            this.prodDisabled = true;
            this.enabledProdId = this.form.get("productId")?.value;
            if (this.form.get("stockTypeId")?.valid && this.form.get("stockTypeId")?.value !== '' && !this.isSaleable) {
              this.medicoUtility.toggleProgressBar(true);
              this.selectedStockType = this.form.get("stockTypeId")?.value;
              //console.log("this.selectedStockType ::"+this.selectedStockType[0]);
              //console.log("this.selectedStockType ::"+this.selectedStockType[1]);
              this.stktrfService.getBatchDetailsForProd(this.form.get("recLocation")?.value, this.form.get("sendLocId")?.value, this.form.get("productId")?.value, this.form.get("tranType")?.value, this.selectedStockType[0]).subscribe(response => {
                this.medicoUtility.toggleProgressBar(true);
                this.batchList = new _angular_material_table__WEBPACK_IMPORTED_MODULE_8__.MatTableDataSource(response.batchList.slice(0));
                this.batchList.paginator = this.paginator;
                this.batchIds = response.batchIds;
                this.batchTrfRates = response.batchTrfRates;
                this.batchStocks = response.batchStocks;
                this.form.get("wthStkQty")?.setValue(response.withheld_stk);
                this.form.get("billableStk")?.setValue(response.billable_stk);
                this.form.get("totalStock")?.setValue(response.total_stock);
                this.medicoUtility.toggleProgressBar(false);
              });
            } else {
              this.medicoUtility.toggleProgressBar(true);
              this.stktrfService.getBatchDetailsForProd(this.form.get("recLocation")?.value, this.form.get("sendLocId")?.value, this.form.get("productId")?.value, this.form.get("tranType")?.value, '').subscribe(response => {
                this.medicoUtility.toggleProgressBar(true);
                this.batchList = new _angular_material_table__WEBPACK_IMPORTED_MODULE_8__.MatTableDataSource(response.batchList.slice(0));
                this.batchList.paginator = this.paginator;
                this.batchIds = response.batchIds;
                this.batchTrfRates = response.batchTrfRates;
                this.batchStocks = response.batchStocks;
                this.form.get("wthStkQty")?.setValue(response.withheld_stk);
                this.form.get("billableStk")?.setValue(response.billable_stk);
                this.form.get("totalStock")?.setValue(response.total_stock);
                this.medicoUtility.toggleProgressBar(false);
              });
            }
          } else {
            this.medicoUtility.toggleProgressBar(false);
            this.form.get('productId')?.reset();
            this.medicoService.popup("Error Message", "In use. Select any other");
          }
        });
      } else {
        this.medicoUtility.toggleProgressBar(false);
      }
    }
    resetDetails() {
      if (this.form.get("productId")?.valid) {
        this.stktrfService.releaseProdLock(this.form.get("productId")?.value, this.form.get("sendLocId")?.value, this.session.EMP_ID).subscribe(response => {});
      }
      this.form.get('productId')?.reset();
      this.form.get('billedQty')?.reset();
      this.form.get('freeQty')?.reset();
      this.form.get('totalStock')?.reset();
      this.form.get('wthStkQty')?.reset();
      this.form.get('billableStk')?.reset();
      this.form.get('billedStkValue')?.reset();
      this.form.get('totalBilledQty')?.reset();
      this.form.get('totalFreeQty')?.reset();
      this.form.get('totalStkTrfvalue')?.reset();
      this.batchList = new _angular_material_table__WEBPACK_IMPORTED_MODULE_8__.MatTableDataSource();
      this.batchIds = new Array();
      this.batchTrfRates = new Array();
      this.batchStocks = new Array();
      this.batchBilledQtys = new Array();
      this.batchFreeQtys = new Array();
      this.batchValues = new Array();
      this.prodDisabled = false;
      this.enabledProdId = '';
    }
    setBatchBilledQty() {
      if (this.form.get('billedQty')?.valid) {
        if (parseFloat(this.form.get("billedQty")?.value) > 0) {
          if (parseFloat(this.form.get("billedQty")?.value) > parseFloat(this.form.get("billableStk")?.value)) {
            this.form.get('billedQty')?.reset();
            this.medicoService.popup("Error Message", "Insufficient Stock ");
          } else {
            //console.log("batchBilledQty::"+this.batchBilledQtys);
            //console.log("batchFreeQty::"+this.batchFreeQtys);
            //console.log("batchTrfRates::"+this.batchTrfRates);
            //console.log("batchStocks::"+this.batchStocks);
            //console.log("batchIds::"+this.batchIds);
            var total = '0';
            var billed = this.form.get("billedQty")?.value;
            for (let i = 0; i < this.batchIds.length; i++) {
              if (parseFloat(this.batchStocks[i]) >= parseFloat(billed)) {
                this.batchFreeQtys[i] = '0.00';
                this.batchBilledQtys[i] = parseFloat(billed).toFixed(2);
                billed = '0.00';
                this.batchValues[i] = (parseFloat(this.batchBilledQtys[i]) * parseFloat(this.batchTrfRates[i])).toFixed(2);
                total = (parseFloat(total) + parseFloat(this.batchValues[i])).toFixed(2);
              } else {
                this.batchFreeQtys[i] = '0.00';
                this.batchBilledQtys[i] = parseFloat(this.batchStocks[i]).toFixed(2);
                billed = (parseFloat(billed) - parseFloat(this.batchStocks[i])).toFixed(2);
                this.batchValues[i] = (parseFloat(this.batchBilledQtys[i]) * parseFloat(this.batchTrfRates[i])).toFixed(2);
                total = (parseFloat(total) + parseFloat(this.batchValues[i])).toFixed(2);
              }
            }
            this.form.get("billedStkValue")?.setValue(total);
            this.form.get("totalBilledQty")?.setValue(this.form.get("billedQty")?.value);
            this.form.get("totalStkTrfvalue")?.setValue(total);
          }
        } else {
          this.form.get('billedQty')?.reset();
        }
      }
    }
    checkBilledQuantity($event, index) {
      var num = $event.target.value;
      if (parseFloat(num) >= 0) {
        $event.target.value = parseFloat(num).toFixed(2);
        var billed_qty = this.form.get("billedQty")?.value;
        var total_soldqty = '0';
        for (let i = 0; i < this.batchIds.length; i++) {
          total_soldqty = (parseFloat(total_soldqty) + parseFloat(this.batchBilledQtys[i])).toFixed(2);
          this.batchValues[index] = (parseFloat(num) * parseFloat(this.batchTrfRates[index])).toFixed(2);
        }
        if (parseFloat(total_soldqty) > parseFloat(billed_qty)) {
          $event.target.value = 0;
          this.batchValues[index] = 0;
          this.medicoService.popup("Error Message", "Quantity mismatch");
        }
      } else {
        $event.target.value = 0;
      }
    }
    save() {
      this.medicoUtility.toggleProgressBar(true);
      this.form.get("currFinYear")?.setValue(this.session.CURR_FIN_YEAR);
      this.form.get("currPeriodCode")?.setValue(this.session.CURR_PERIOD);
      this.form.get("empId")?.setValue(this.session.EMP_ID);
      this.form.get("gstInd")?.setValue(this.session.gst_ind);
      this.form.get("batchIds")?.setValue(this.batchIds);
      this.form.get("batchBilledQtys")?.setValue(this.batchBilledQtys);
      this.form.get("batchFreeQtys")?.setValue(this.batchFreeQtys);
      this.form.get("batchTrfRates")?.setValue(this.batchTrfRates);
      if (!this.isSaleable) {
        this.selectedStockType = this.form.get("stockTypeId")?.value;
        this.form.get("stockTypeId")?.setValue(this.selectedStockType[0]);
        this.form.get("stockTypeDetId")?.setValue(this.selectedStockType[1]);
      }
      if (this.headerFreezed) {
        this.form.get("tranType")?.setValue(this.tranTypeTemp);
        this.form.get("recLocation")?.setValue(this.recLocationTemp);
        this.form.get("nilGstInd")?.setValue(this.nilGstIndTemp);
      } else {
        this.tranTypeTemp = this.form.get("tranType")?.value;
        this.recLocationTemp = this.form.get("recLocation")?.value;
        this.nilGstIndTemp = this.form.get("nilGstInd")?.value;
      }
      if (this.form.valid) {
        this.medicoUtility.toggleProgressBar(true);
        try {
          this.stktrfService.saveStkTrf(this.form).subscribe(response => {
            if (response.DATA_SAVED) {
              this.medicoService.openJustSnackBar(response.RESPONSE_MESSAGE, this.constants.snack_bar_milliseconds_8k);
              this.form.get("headerId")?.setValue(Number(response.headerId));
              if (!this.headerFreezed) {
                this.form.get("stktrfNo")?.setValue(response.trans_no);
              }
              if (this.form.get("tranType")?.value == '85') {
                if (this.prodList) this.prodList = this.prodList.filter(x => x.prodId !== this.form.get("productId")?.value);
              } else {
                this.form.get("stockTypeId")?.setValue(this.selectedStockType);
                if (this.prodList) this.prodList = this.prodList.filter(x => x.prodId !== this.form.get("productId")?.value);
              }
              this.resetDetails();
              this.form.markAsUntouched();
              this.headerFreezed = true;
              this.medicoUtility.toggleProgressBar(false);
              this.prodSelection.focus();
            } else {
              this.medicoUtility.toggleProgressBar(false);
              this.medicoService.openJustSnackBar(response.RESPONSE_MESSAGE, this.constants.snack_bar_milliseconds_8k);
            }
          }, error => {
            this.medicoUtility.toggleProgressBar(false);
            this.medicoService.openJustSnackBar("Network Error Occurred!", this.constants.snack_bar_milliseconds_8k);
          });
        } catch (error) {
          this.medicoUtility.toggleProgressBar(false);
          this.medicoService.popup("Stock Transfer", "Network Error Occurred!");
        }
      } else {
        this.medicoUtility.toggleProgressBar(false);
      }
    }
    createNewStkTrf() {
      this.medicoUtility.toggleProgressBar(true);
      this.resetDetails();
      this.form.reset();
      this.form.markAsUntouched();
      this.form.get('productId')?.reset();
      this.form.get('billedQty')?.reset();
      this.form.get('freeQty')?.reset();
      this.form.get('totalStock')?.reset();
      this.form.get('wthStkQty')?.reset();
      this.form.get('billableStk')?.reset();
      this.form.get('billedStkValue')?.reset();
      this.form.get('totalBilledQty')?.reset();
      this.form.get('totalFreeQty')?.reset();
      this.form.get('totalStkTrfvalue')?.reset();
      this.form.get('stockTypeId')?.reset();
      this.batchList = new _angular_material_table__WEBPACK_IMPORTED_MODULE_8__.MatTableDataSource();
      this.batchIds = new Array();
      this.batchTrfRates = new Array();
      this.batchStocks = new Array();
      this.batchBilledQtys = new Array();
      this.batchFreeQtys = new Array();
      this.batchValues = new Array();
      this.prodDisabled = false;
      this.enabledProdId = '';
      this.dispatchParameters = new Array();
      this.recLocationList = new Array();
      this.nilGstOptionsList = new Array();
      this.tranTypeList = new Array();
      this.prodList = new Array();
      this.isSaleable = true;
      this.stockTypes = new Array();
      this.headerFreezed = false;
      this.tranTypeTemp = '';
      this.recLocationTemp = '';
      this.nilGstIndTemp = '';
      this.form.get("headerId")?.setValue(0);
      this.form.get("saveMode")?.setValue(this.constants.ENTRY_MODE.toString());
      this.session = this.medicoUtility.getSessionVariables();
      this.medicoUtility.toggleProgressBar(false);
      this.nilGstOptionsList = [{
        key: "N",
        value: "No"
      }, {
        key: "Y",
        value: "Yes"
      }];
      this.stktrfService.getDataForStkTrfEntry(this.session.EMP_ID).subscribe(response => {
        this.form.get("sendLocation")?.setValue(response.sendLocName);
        this.form.get("sendSubCompId")?.setValue(response.sendSubCompId);
        this.form.get("sendLocId")?.setValue(response.sendLocId);
        this.form.get("sendLocStateId")?.setValue(response.sendLocStateId);
        this.recLocationList = response.recLocations;
        this.dispatchParameters = response.dispatchParametersList;
        this.form.get("trfType")?.setValue(response.trfType);
        this.form.get("trfDate")?.setValue(response.stkTrfDate);
        this.medicoUtility.toggleProgressBar(false);
        if (this.dispatchParameters) this.tranTypeList = [{
          key: "85",
          value: this.dispatchParameters[0].sgprmdet_disp_nm
        }, {
          key: "86",
          value: this.dispatchParameters[1].sgprmdet_disp_nm
        }];
        this.form.get("nilGstInd")?.setValue(this.nilGstOptionsList[0].key);
        this.form.get("tranType")?.setValue(this.tranTypeList[0].key);
        this.recvloc.focus();
      });
      // this.tranTypeList = [ { key: "85", value: "Saleable"},{ key: "86", value: "Non Saleable"}];
      // this.form.get("nilGstInd")?.setValue(this.nilGstOptionsList[0].key);
      // this.form.get("tranType")?.setValue(this.tranTypeList[0].key);
      // this.getDataForStkTrfEntry();
    }
    checkDetails() {
      if (this.form.get("headerId")?.value !== 0) {
        this.medicoUtility.toggleProgressBar(true);
        this.stktrfService.getSavedProdList(this.form.get("headerId")?.value, this.form.get("sendLocId")?.value).subscribe(response => {
          if (response.savedProdList.length > 0) {
            const dialogRef = this.dialog.open(StockTrfDetailComponent, {
              width: '1000px',
              data: {
                list: response.savedProdList
              }
            });
          }
          this.medicoUtility.toggleProgressBar(false);
        });
      }
    }
    unlockprod() {
      this.stktrfService.unlockProducts().subscribe(response => {});
      this.router.navigate(['/medico-user/home']);
    }
    static ɵfac = function StktrfEntryComponent_Factory(__ngFactoryType__) {
      return new (__ngFactoryType__ || StktrfEntryComponent)(_angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵdirectiveInject"](_services_user_user_service__WEBPACK_IMPORTED_MODULE_1__.UserService), _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵdirectiveInject"](src_app_services_stk_trf_stktrf_service__WEBPACK_IMPORTED_MODULE_2__.StktrfService), _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵdirectiveInject"](src_app_classes_medico_utility__WEBPACK_IMPORTED_MODULE_3__.MedicoUtility), _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵdirectiveInject"](src_app_services_medico_medico_service__WEBPACK_IMPORTED_MODULE_4__.MedicoService), _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵdirectiveInject"](_angular_forms__WEBPACK_IMPORTED_MODULE_7__.FormBuilder), _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵdirectiveInject"](_angular_material_dialog__WEBPACK_IMPORTED_MODULE_9__.MatDialog), _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵdirectiveInject"](_angular_core__WEBPACK_IMPORTED_MODULE_6__.ElementRef), _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵdirectiveInject"](_angular_router__WEBPACK_IMPORTED_MODULE_10__.Router));
    };
    static ɵcmp = /*@__PURE__*/_angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵdefineComponent"]({
      type: StktrfEntryComponent,
      selectors: [["app-stktrf-entry"]],
      viewQuery: function StktrfEntryComponent_Query(rf, ctx) {
        if (rf & 1) {
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵviewQuery"](_c0, 5);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵviewQuery"](_c1, 5);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵviewQuery"](_angular_material_paginator__WEBPACK_IMPORTED_MODULE_11__.MatPaginator, 5);
        }
        if (rf & 2) {
          let _t;
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵqueryRefresh"](_t = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵloadQuery"]()) && (ctx.prodSelection = _t.first);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵqueryRefresh"](_t = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵloadQuery"]()) && (ctx.recvloc = _t.first);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵqueryRefresh"](_t = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵloadQuery"]()) && (ctx.paginator = _t.first);
        }
      },
      decls: 127,
      vars: 59,
      consts: [["recvloc", ""], ["prodSelection", ""], [3, "formGroup"], [1, "pl-1", "pr-1", "pb-1", "container"], ["fxLayout", "row", "fxLayout.lt-md", "column"], ["fxFlex", "100%"], ["fxLayout", "row", "fxLayout.lt-md", "column", 1, "pl-1", "pt-1", 2, "margin-bottom", "-12px"], ["fxFlex", "25%", "appearance", "outline", 3, "ngClass"], ["tabindex", "1", "formControlName", "tranType", "matNativeControl", "", "required", "", 3, "selectionChange", "ngClass", "disabled"], [3, "value", 4, "ngFor", "ngForOf"], ["matInput", "", "type", "text", "formControlName", "sendLocation", "readonly", "", 3, "tabindex"], ["tabindex", "2", "formControlName", "recLocation", "matNativeControl", "", "required", "", 3, "selectionChange", "ngClass", "disabled"], ["matInput", "", "type", "text", "formControlName", "trfType", "readonly", "", "required", "", 3, "tabindex"], ["fxLayout", "row", "fxLayout.lt-md", "column", 1, "pl-1", "pt-0"], ["fxFlex", "24.3%", "appearance", "outline", 3, "ngClass"], ["matInput", "", "type", "text", "formControlName", "stktrfNo", "readonly", "", 3, "tabindex"], ["matInput", "", "type", "text", "formControlName", "trfDate", "readonly", "", "required", "", 3, "tabindex"], ["tabindex", "3", "formControlName", "nilGstInd", "matNativeControl", "", "required", "", 3, "selectionChange", "ngClass", "disabled"], ["fxLayout", "row", "fxLayout.lt-md", "column", 1, "p-1", "pb-0"], ["fxFlex", "100%", 1, "purple-font", "bold-font", "help-title"], ["fxLayout", "row", "fxLayout.lt-md", "column", 1, "pl-1", "pt-0", 2, "margin-bottom", "-12px"], ["fxFlex", "24.2%", "appearance", "outline", 3, "ngClass"], ["formControlName", "stockTypeId", "matNativeControl", "", 3, "selectionChange", "ngClass", "disabled"], ["fxFlex", "49.3%", "appearance", "outline", 3, "ngClass"], ["tabindex", "4", "formControlName", "productId", "matNativeControl", "", "required", "", 3, "selectionChange", "ngClass"], [3, "matTooltip", "value", "disabled", 4, "ngFor", "ngForOf"], ["mat-raised-button", "", "type", "button", 1, "mr-r-10", "color-3", 3, "click"], ["fxLayout", "row", "fxLayout.lt-md", "column", 1, "pl-1", "pt-0", "pb-0"], ["tabindex", "5", "appNumbersOnly", "", "step", "0.01", "matInput", "", "type", "text", "formControlName", "billedQty", "required", "", 1, "text-right", 3, "change"], ["matInput", "", "type", "text", "formControlName", "billableStk", "readonly", "", 1, "text-right", 3, "tabindex"], ["matInput", "", "type", "text", "formControlName", "wthStkQty", "readonly", "", 1, "text-right", 3, "tabindex"], ["matInput", "", "type", "text", "formControlName", "totalStock", "readonly", "", 1, "text-right", 3, "tabindex"], ["fxLayout", "column", "fxLayout.lt-md", "column"], [1, "table-container", "mat-elevation-z2", "width-only-100"], ["mat-table", "", "matSort", "", 1, "table-borders-right", "width-only-100", "table-th-bg-primary", "thin-th", "mat-td-p", "mat-th-p", 3, "dataSource"], ["matColumnDef", "BatchNo"], ["mat-header-cell", "", 4, "matHeaderCellDef"], ["mat-cell", "", 4, "matCellDef"], ["matColumnDef", "MfgDate"], ["mat-header-cell", "", "class", "right-align", 4, "matHeaderCellDef"], ["mat-cell", "", "class", "right-align", 4, "matCellDef"], ["matColumnDef", "ExpiryDate"], ["matColumnDef", "Rate"], ["matColumnDef", "TaxRate"], ["matColumnDef", "Stock"], ["matColumnDef", "BilledQty"], ["matColumnDef", "Value"], ["mat-header-row", "", "mat-sort-header", "", "class", "table-row1", 4, "matHeaderRowDef", "matHeaderRowDefSticky"], ["mat-row", "", "class", "table-row1", 4, "matRowDef", "matRowDefColumns"], ["showFirstLastButtons", "", 3, "pageSizeOptions"], ["fxLayout", "row", "fxLayout.lt-md", "column", 1, "pl-1", "pt-1"], ["fxFlex", "15%", "appearance", "outline", 3, "ngClass"], ["matInput", "", "type", "text", "formControlName", "totalBilledQty", "readonly", "", 1, "text-right", 3, "tabindex"], ["matInput", "", "type", "text", "formControlName", "totalStkTrfvalue", "readonly", "", 1, "text-right", 3, "tabindex"], ["fxLayout", "row", 1, "mt-1"], ["mat-raised-button", "", "type", "button", "tabindex", "7", 3, "click"], ["mat-raised-button", "", "type", "button", "tabindex", "8", 1, "mr-r-10", "color-9", 3, "click"], ["mat-raised-button", "", "type", "button", 3, "click"], [3, "value"], [3, "matTooltip", "value", "disabled"], ["mat-header-cell", ""], ["mat-cell", ""], ["mat-header-cell", "", 1, "right-align"], ["mat-cell", "", 1, "right-align"], ["appNumbersOnly", "", "step", "0.01", "matInput", "", "type", "text", 1, "text-right", "mat-input-batdetails", 3, "ngModelChange", "change", "ngModel", "ngModelOptions"], ["mat-header-row", "", "mat-sort-header", "", 1, "table-row1"], ["mat-row", "", 1, "table-row1"]],
      template: function StktrfEntryComponent_Template(rf, ctx) {
        if (rf & 1) {
          const _r1 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵgetCurrentView"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "form", 2)(1, "div", 3)(2, "div", 4)(3, "div", 5)(4, "div", 6)(5, "mat-form-field", 7)(6, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](7, "Dispatchable/Non Dispatchable");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](8, "mat-select", 8);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵlistener"]("selectionChange", function StktrfEntryComponent_Template_mat_select_selectionChange_8_listener() {
            _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵrestoreView"](_r1);
            return _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵresetView"](ctx.getStockTypes());
          });
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](9, StktrfEntryComponent_mat_option_9_Template, 2, 2, "mat-option", 9);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](10, "mat-form-field", 7)(11, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](12, "Sending Location");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelement"](13, "input", 10);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](14, "mat-form-field", 7)(15, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](16, "Receiving Location");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](17, "mat-select", 11, 0);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵlistener"]("selectionChange", function StktrfEntryComponent_Template_mat_select_selectionChange_17_listener() {
            _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵrestoreView"](_r1);
            return _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵresetView"](ctx.getProductList());
          });
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](19, StktrfEntryComponent_mat_option_19_Template, 2, 2, "mat-option", 9);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](20, "mat-form-field", 7)(21, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](22, "Transfer Type");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelement"](23, "input", 12);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](24, "div", 13)(25, "mat-form-field", 14)(26, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](27, "Transfer No");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelement"](28, "input", 15);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](29, "mat-form-field", 14)(30, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](31, "Transfer Date");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelement"](32, "input", 16);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](33, "mat-form-field", 14)(34, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](35, "NIL GST Products");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](36, "mat-select", 17);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵlistener"]("selectionChange", function StktrfEntryComponent_Template_mat_select_selectionChange_36_listener() {
            _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵrestoreView"](_r1);
            return _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵresetView"](ctx.getProductList());
          });
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](37, StktrfEntryComponent_mat_option_37_Template, 2, 2, "mat-option", 9);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()()()()();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](38, "div", 4)(39, "div", 5)(40, "div", 18)(41, "span", 19);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](42, "Transfer Order");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](43, "div", 20)(44, "mat-form-field", 21)(45, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](46, "Stock Type");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](47, "mat-select", 22);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵlistener"]("selectionChange", function StktrfEntryComponent_Template_mat_select_selectionChange_47_listener() {
            _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵrestoreView"](_r1);
            return _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵresetView"](ctx.getProductList());
          });
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](48, StktrfEntryComponent_mat_option_48_Template, 2, 5, "mat-option", 9);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](49, "mat-form-field", 23)(50, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](51, "Product");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](52, "mat-select", 24, 1);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵlistener"]("selectionChange", function StktrfEntryComponent_Template_mat_select_selectionChange_52_listener() {
            _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵrestoreView"](_r1);
            return _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵresetView"](ctx.getBatchDetails());
          });
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](54, StktrfEntryComponent_mat_option_54_Template, 2, 4, "mat-option", 25);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](55, "button", 26);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵlistener"]("click", function StktrfEntryComponent_Template_button_click_55_listener() {
            _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵrestoreView"](_r1);
            return _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵresetView"](ctx.resetDetails());
          });
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](56, "Reset");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](57, "div", 27)(58, "mat-form-field", 7)(59, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](60, "Quantity");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](61, "input", 28);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵlistener"]("change", function StktrfEntryComponent_Template_input_change_61_listener() {
            _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵrestoreView"](_r1);
            return _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵresetView"](ctx.setBatchBilledQty());
          });
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](62, "mat-form-field", 7)(63, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](64, "Transfer Stock");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelement"](65, "input", 29);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](66, "mat-form-field", 7)(67, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](68, "Withheld Stock");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelement"](69, "input", 30);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](70, "mat-form-field", 7)(71, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](72, "Total Stock");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelement"](73, "input", 31);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()()()();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](74, "div", 32)(75, "div", 5)(76, "div", 18)(77, "span", 19);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](78, "Batchwise Dispatch of the Transfer Order");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](79, "div", 18)(80, "div", 33)(81, "table", 34);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerStart"](82, 35);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](83, StktrfEntryComponent_th_83_Template, 2, 0, "th", 36)(84, StktrfEntryComponent_td_84_Template, 2, 1, "td", 37);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerStart"](85, 38);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](86, StktrfEntryComponent_th_86_Template, 2, 0, "th", 39)(87, StktrfEntryComponent_td_87_Template, 2, 1, "td", 40);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerStart"](88, 41);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](89, StktrfEntryComponent_th_89_Template, 2, 0, "th", 39)(90, StktrfEntryComponent_td_90_Template, 2, 1, "td", 40);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerStart"](91, 42);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](92, StktrfEntryComponent_th_92_Template, 2, 0, "th", 39)(93, StktrfEntryComponent_td_93_Template, 2, 1, "td", 40);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerStart"](94, 43);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](95, StktrfEntryComponent_th_95_Template, 2, 0, "th", 39)(96, StktrfEntryComponent_td_96_Template, 2, 1, "td", 40);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerStart"](97, 44);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](98, StktrfEntryComponent_th_98_Template, 2, 0, "th", 39)(99, StktrfEntryComponent_td_99_Template, 2, 1, "td", 40);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerStart"](100, 45);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](101, StktrfEntryComponent_th_101_Template, 2, 0, "th", 39)(102, StktrfEntryComponent_td_102_Template, 2, 3, "td", 40);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerStart"](103, 46);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](104, StktrfEntryComponent_th_104_Template, 2, 0, "th", 39)(105, StktrfEntryComponent_td_105_Template, 2, 1, "td", 40);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](106, StktrfEntryComponent_tr_106_Template, 1, 0, "tr", 47)(107, StktrfEntryComponent_tr_107_Template, 1, 0, "tr", 48);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelement"](108, "mat-paginator", 49);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()()()();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](109, "div", 50)(110, "mat-form-field", 51)(111, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](112, "Total Transfer Qty");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelement"](113, "input", 52);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](114, "mat-form-field", 51)(115, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](116, "Total Value");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelement"](117, "input", 53);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](118, "div", 54)(119, "button", 55);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵlistener"]("click", function StktrfEntryComponent_Template_button_click_119_listener() {
            _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵrestoreView"](_r1);
            return _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵresetView"](ctx.save());
          });
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](120, "Save");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](121, "button", 26);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵlistener"]("click", function StktrfEntryComponent_Template_button_click_121_listener() {
            _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵrestoreView"](_r1);
            return _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵresetView"](ctx.checkDetails());
          });
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](122, "View");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](123, "button", 56);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵlistener"]("click", function StktrfEntryComponent_Template_button_click_123_listener() {
            _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵrestoreView"](_r1);
            return _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵresetView"](ctx.createNewStkTrf());
          });
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](124, "New Stock Transfer");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](125, "button", 57);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵlistener"]("click", function StktrfEntryComponent_Template_button_click_125_listener() {
            _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵrestoreView"](_r1);
            return _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵresetView"](ctx.unlockprod());
          });
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](126, "Exit");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()()()();
        }
        if (rf & 2) {
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("formGroup", ctx.form);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"](2);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵclassMap"](ctx.constants.page_div_class);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("ngClass", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("ngClass", "mr-r-10")("disabled", ctx.headerFreezed);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("ngForOf", ctx.tranTypeList);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("ngClass", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("tabindex", -1);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("ngClass", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("ngClass", "mr-r-10")("disabled", ctx.headerFreezed);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"](2);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("ngForOf", ctx.recLocationList);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("ngClass", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("tabindex", -1);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"](2);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("ngClass", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("tabindex", -1);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("ngClass", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("tabindex", -1);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("ngClass", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("ngClass", "mr-r-10")("disabled", ctx.headerFreezed);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("ngForOf", ctx.nilGstOptionsList);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵclassMapInterpolate1"]("", ctx.constants.page_div_class, " pb-0");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"](6);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("ngClass", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("ngClass", "mr-r-10")("disabled", ctx.isSaleable);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("ngForOf", ctx.stockTypes);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("ngClass", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("ngClass", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"](2);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("ngForOf", ctx.prodList);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"](4);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("ngClass", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"](4);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("ngClass", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("tabindex", -1);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("ngClass", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("tabindex", -1);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("ngClass", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("tabindex", -1);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵclassMap"](ctx.constants.page_div_class);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"](7);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("dataSource", ctx.batchList);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"](25);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("matHeaderRowDef", ctx.displayedColumns)("matHeaderRowDefSticky", true);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("matRowDefColumns", ctx.displayedColumns);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("pageSizeOptions", ctx.constants.paginator_upto_50);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"](2);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("ngClass", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("tabindex", -1);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("ngClass", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("tabindex", -1);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"](2);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵclassMapInterpolate1"]("mr-r-10  ", ctx.constants.save_btn_class, "");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"](6);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵclassMapInterpolate1"]("mr-r-10 ", ctx.constants.exit_btn_class, "");
        }
      },
      dependencies: [_angular_common__WEBPACK_IMPORTED_MODULE_12__.NgClass, _angular_common__WEBPACK_IMPORTED_MODULE_12__.NgForOf, _angular_forms__WEBPACK_IMPORTED_MODULE_7__["ɵNgNoValidate"], _angular_forms__WEBPACK_IMPORTED_MODULE_7__.DefaultValueAccessor, _angular_forms__WEBPACK_IMPORTED_MODULE_7__.NgControlStatus, _angular_forms__WEBPACK_IMPORTED_MODULE_7__.NgControlStatusGroup, _angular_forms__WEBPACK_IMPORTED_MODULE_7__.RequiredValidator, _angular_forms__WEBPACK_IMPORTED_MODULE_7__.NgModel, _angular_forms__WEBPACK_IMPORTED_MODULE_7__.FormGroupDirective, _angular_forms__WEBPACK_IMPORTED_MODULE_7__.FormControlName, _angular_material_form_field__WEBPACK_IMPORTED_MODULE_13__.MatFormField, _angular_material_form_field__WEBPACK_IMPORTED_MODULE_13__.MatLabel, _angular_material_button__WEBPACK_IMPORTED_MODULE_14__.MatButton, _angular_flex_layout_flex__WEBPACK_IMPORTED_MODULE_15__.DefaultLayoutDirective, _angular_flex_layout_flex__WEBPACK_IMPORTED_MODULE_15__.DefaultFlexDirective, _angular_flex_layout_extended__WEBPACK_IMPORTED_MODULE_16__.DefaultClassDirective, _angular_material_input__WEBPACK_IMPORTED_MODULE_17__.MatInput, _angular_material_select__WEBPACK_IMPORTED_MODULE_18__.MatSelect, _angular_material_core__WEBPACK_IMPORTED_MODULE_19__.MatOption, _angular_material_table__WEBPACK_IMPORTED_MODULE_8__.MatTable, _angular_material_table__WEBPACK_IMPORTED_MODULE_8__.MatHeaderCellDef, _angular_material_table__WEBPACK_IMPORTED_MODULE_8__.MatHeaderRowDef, _angular_material_table__WEBPACK_IMPORTED_MODULE_8__.MatColumnDef, _angular_material_table__WEBPACK_IMPORTED_MODULE_8__.MatCellDef, _angular_material_table__WEBPACK_IMPORTED_MODULE_8__.MatRowDef, _angular_material_table__WEBPACK_IMPORTED_MODULE_8__.MatHeaderCell, _angular_material_table__WEBPACK_IMPORTED_MODULE_8__.MatCell, _angular_material_table__WEBPACK_IMPORTED_MODULE_8__.MatHeaderRow, _angular_material_table__WEBPACK_IMPORTED_MODULE_8__.MatRow, _angular_material_paginator__WEBPACK_IMPORTED_MODULE_11__.MatPaginator, _angular_material_tooltip__WEBPACK_IMPORTED_MODULE_20__.MatTooltip, _directives_utility_numbers_only_directive__WEBPACK_IMPORTED_MODULE_5__.NumbersOnlyDirective],
      styles: [".mat-input-batdetails[_ngcontent-%COMP%]{ width:90px !important;}\n.mat-form-field[_ngcontent-%COMP%] {width: auto !important;font-size: 17px;}\n.mat-select-value[_ngcontent-%COMP%] {max-width: 100%;width: auto;}  \n.table-row1[_ngcontent-%COMP%]{ height: 35px;}\n\n.mat-form-field-can-float.mat-form-field-should-float[_ngcontent-%COMP%]   .mat-form-field-label[_ngcontent-%COMP%], \n.mat-form-field-can-float[_ngcontent-%COMP%]   .mat-input-server[_ngcontent-%COMP%]:focus    + .mat-form-field-label-wrapper[_ngcontent-%COMP%]   .mat-form-field-label[_ngcontent-%COMP%] {\n  transform: translateY(-1.34375em) scale(1) !important; \n\n  margin-top: 0 !important;\n}\n\nbutton[_ngcontent-%COMP%]:focus {\n  border : 2px dotted black;\n}\n.right-align[_ngcontent-%COMP%] {\n  text-align: right;\n}\n\n\n\n\n\n\n/*# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbIndlYnBhY2s6Ly8uL3NyYy9hcHAvY29tcG9uZW50cy90cmFuc2FjdGlvbi9zdGt0cmYtZW50cnkvc3RrdHJmLWVudHJ5LmNvbXBvbmVudC5jc3MiXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IkFBQUEsdUJBQXVCLHFCQUFxQixDQUFDO0FBQzdDLGlCQUFpQixzQkFBc0IsQ0FBQyxlQUFlLENBQUM7QUFDeEQsbUJBQW1CLGVBQWUsQ0FBQyxXQUFXLENBQUM7QUFDL0MsYUFBYSxZQUFZLENBQUM7O0FBRTFCOztFQUVFLHFEQUFxRCxFQUFFLDJCQUEyQjtFQUNsRix3QkFBd0I7QUFDMUI7O0FBRUE7RUFDRSx5QkFBeUI7QUFDM0I7QUFDQTtFQUNFLGlCQUFpQjtBQUNuQiIsInNvdXJjZXNDb250ZW50IjpbIi5tYXQtaW5wdXQtYmF0ZGV0YWlsc3sgd2lkdGg6OTBweCAhaW1wb3J0YW50O31cclxuLm1hdC1mb3JtLWZpZWxkIHt3aWR0aDogYXV0byAhaW1wb3J0YW50O2ZvbnQtc2l6ZTogMTdweDt9XHJcbi5tYXQtc2VsZWN0LXZhbHVlIHttYXgtd2lkdGg6IDEwMCU7d2lkdGg6IGF1dG87fSAgXHJcbi50YWJsZS1yb3cxeyBoZWlnaHQ6IDM1cHg7fVxyXG5cclxuLm1hdC1mb3JtLWZpZWxkLWNhbi1mbG9hdC5tYXQtZm9ybS1maWVsZC1zaG91bGQtZmxvYXQgLm1hdC1mb3JtLWZpZWxkLWxhYmVsLFxyXG4ubWF0LWZvcm0tZmllbGQtY2FuLWZsb2F0IC5tYXQtaW5wdXQtc2VydmVyOmZvY3VzICsgLm1hdC1mb3JtLWZpZWxkLWxhYmVsLXdyYXBwZXIgLm1hdC1mb3JtLWZpZWxkLWxhYmVsIHtcclxuICB0cmFuc2Zvcm06IHRyYW5zbGF0ZVkoLTEuMzQzNzVlbSkgc2NhbGUoMSkgIWltcG9ydGFudDsgLyogb3JpZ2luYWxseSBzY2FsZSgwLjc1KSAqL1xyXG4gIG1hcmdpbi10b3A6IDAgIWltcG9ydGFudDtcclxufVxyXG5cclxuYnV0dG9uOmZvY3VzIHtcclxuICBib3JkZXIgOiAycHggZG90dGVkIGJsYWNrO1xyXG59XHJcbi5yaWdodC1hbGlnbiB7XHJcbiAgdGV4dC1hbGlnbjogcmlnaHQ7XHJcbn1cclxuXHJcblxyXG5cclxuXHJcblxyXG4iXSwic291cmNlUm9vdCI6IiJ9 */"]
    });
  }
  return StktrfEntryComponent;
})();
let StockTrfDetailComponent = /*#__PURE__*/(() => {
  class StockTrfDetailComponent {
    dialogRef;
    data;
    constructor(dialogRef, data) {
      this.dialogRef = dialogRef;
      this.data = data;
    }
    onNoClick() {
      this.dialogRef.close();
    }
    static ɵfac = function StockTrfDetailComponent_Factory(__ngFactoryType__) {
      return new (__ngFactoryType__ || StockTrfDetailComponent)(_angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵdirectiveInject"](_angular_material_dialog__WEBPACK_IMPORTED_MODULE_9__.MatDialogRef), _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵdirectiveInject"](_angular_material_dialog__WEBPACK_IMPORTED_MODULE_9__.MAT_DIALOG_DATA));
    };
    static ɵcmp = /*@__PURE__*/_angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵdefineComponent"]({
      type: StockTrfDetailComponent,
      selectors: [["stk-trf-detail-dialog"]],
      decls: 23,
      vars: 1,
      consts: [["mat-dialog-title", ""], ["mat-dialog-content", ""], [1, "mytable", "table-full-width"], [4, "ngFor", "ngForOf"], ["mat-dialog-actions", "", 1, "mt-1"], ["mat-raised-button", "", "color", "primary", "mat-dialog-close", ""], ["align", "right"]],
      template: function StockTrfDetailComponent_Template(rf, ctx) {
        if (rf & 1) {
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "h1", 0);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](1, "Product Details");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](2, "div", 1)(3, "table", 2)(4, "thead")(5, "tr")(6, "th");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](7, "Transfer No");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](8, "th");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](9, "Product Name");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](10, "th");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](11, "Batch Number");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](12, "th");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](13, "Sold Qty");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](14, "th");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](15, "Rate");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](16, "th");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](17, "Value");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()()();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](18, "tbody");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](19, StockTrfDetailComponent_tr_19_Template, 13, 6, "tr", 3);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()()();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](20, "div", 4)(21, "button", 5);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](22, "Close");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()();
        }
        if (rf & 2) {
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"](19);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("ngForOf", ctx.data.list);
        }
      },
      dependencies: [_angular_common__WEBPACK_IMPORTED_MODULE_12__.NgForOf, _angular_material_button__WEBPACK_IMPORTED_MODULE_14__.MatButton, _angular_material_dialog__WEBPACK_IMPORTED_MODULE_9__.MatDialogClose, _angular_material_dialog__WEBPACK_IMPORTED_MODULE_9__.MatDialogTitle, _angular_material_dialog__WEBPACK_IMPORTED_MODULE_9__.MatDialogActions, _angular_material_dialog__WEBPACK_IMPORTED_MODULE_9__.MatDialogContent],
      encapsulation: 2
    });
  }
  return StockTrfDetailComponent;
})();

/***/ }),

/***/ 89046:
/*!*********************************************************************************!*\
  !*** ./src/app/components/transaction/stktrf-modify/stktrf-modify.component.ts ***!
  \*********************************************************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   StktrfModifyComponent: () => (/* binding */ StktrfModifyComponent),
/* harmony export */   StockTrfModifyDetailComponent: () => (/* binding */ StockTrfModifyDetailComponent)
/* harmony export */ });
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! @angular/forms */ 34456);
/* harmony import */ var src_app_classes_constants__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! src/app/classes/constants */ 5556);
/* harmony import */ var _angular_material_table__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! @angular/material/table */ 77697);
/* harmony import */ var _angular_material_paginator__WEBPACK_IMPORTED_MODULE_11__ = __webpack_require__(/*! @angular/material/paginator */ 24624);
/* harmony import */ var _angular_material_dialog__WEBPACK_IMPORTED_MODULE_8__ = __webpack_require__(/*! @angular/material/dialog */ 12587);
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! @angular/core */ 37580);
/* harmony import */ var src_app_services_stk_trf_stktrf_service__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! src/app/services/stk-trf/stktrf.service */ 12644);
/* harmony import */ var src_app_classes_medico_utility__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! src/app/classes/medico-utility */ 79955);
/* harmony import */ var src_app_services_medico_medico_service__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! src/app/services/medico/medico.service */ 353);
/* harmony import */ var _angular_common__WEBPACK_IMPORTED_MODULE_9__ = __webpack_require__(/*! @angular/common */ 60316);
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_10__ = __webpack_require__(/*! @angular/router */ 95072);
/* harmony import */ var _angular_material_form_field__WEBPACK_IMPORTED_MODULE_12__ = __webpack_require__(/*! @angular/material/form-field */ 24950);
/* harmony import */ var _angular_material_button__WEBPACK_IMPORTED_MODULE_13__ = __webpack_require__(/*! @angular/material/button */ 84175);
/* harmony import */ var _angular_flex_layout_flex__WEBPACK_IMPORTED_MODULE_14__ = __webpack_require__(/*! @angular/flex-layout/flex */ 91447);
/* harmony import */ var _angular_flex_layout_extended__WEBPACK_IMPORTED_MODULE_15__ = __webpack_require__(/*! @angular/flex-layout/extended */ 52913);
/* harmony import */ var _angular_material_input__WEBPACK_IMPORTED_MODULE_16__ = __webpack_require__(/*! @angular/material/input */ 95541);
/* harmony import */ var _angular_material_icon__WEBPACK_IMPORTED_MODULE_17__ = __webpack_require__(/*! @angular/material/icon */ 93840);
/* harmony import */ var _angular_material_select__WEBPACK_IMPORTED_MODULE_18__ = __webpack_require__(/*! @angular/material/select */ 25175);
/* harmony import */ var _angular_material_core__WEBPACK_IMPORTED_MODULE_19__ = __webpack_require__(/*! @angular/material/core */ 74646);
/* harmony import */ var _angular_material_datepicker__WEBPACK_IMPORTED_MODULE_20__ = __webpack_require__(/*! @angular/material/datepicker */ 61977);
/* harmony import */ var _angular_material_tooltip__WEBPACK_IMPORTED_MODULE_21__ = __webpack_require__(/*! @angular/material/tooltip */ 80640);
/* harmony import */ var _directives_utility_numbers_only_directive__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ../../../directives/utility/numbers-only.directive */ 22128);


























const _c0 = (a0, a1) => [a0, a1];
const _c1 = () => ({
  standalone: true
});
function StktrfModifyComponent_mat_option_9_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "mat-option", 60);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const list_r2 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("value", list_r2.key);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](list_r2.value);
  }
}
function StktrfModifyComponent_mat_option_18_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "mat-option", 60);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const l_r3 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("value", l_r3.loc_id);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](l_r3.loc_nm);
  }
}
function StktrfModifyComponent_mat_option_37_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "mat-option", 60);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const list_r4 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("value", list_r4.key);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](list_r4.value);
  }
}
function StktrfModifyComponent_mat_option_42_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "mat-option", 60);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const t_r5 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("value", t_r5.trf_id);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](t_r5.trf_no);
  }
}
function StktrfModifyComponent_mat_option_60_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "mat-option", 60);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const s_r6 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("value", _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵpureFunction2"](2, _c0, s_r6.sgprmdet_nm, s_r6.sg_prmdet_id));
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](s_r6.sgprmdet_disp_nm);
  }
}
function StktrfModifyComponent_mat_option_65_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "mat-option", 61);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const list_r7 = ctx.$implicit;
    const ctx_r7 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵpropertyInterpolate"]("matTooltip", list_r7.prodName);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("value", list_r7.prodId)("disabled", list_r7.prodId !== ctx_r7.enabledProdId && ctx_r7.prodDisabled);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](list_r7.prodName);
  }
}
function StktrfModifyComponent_div_72_Template(rf, ctx) {
  if (rf & 1) {
    const _r9 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵgetCurrentView"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "div")(1, "input", 62);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵlistener"]("change", function StktrfModifyComponent_div_72_Template_input_change_1_listener() {
      _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵrestoreView"](_r9);
      const ctx_r7 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"]();
      return _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵresetView"](ctx_r7.setBatchBilledQty());
    });
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("tabindex", -1);
  }
}
function StktrfModifyComponent_div_73_Template(rf, ctx) {
  if (rf & 1) {
    const _r10 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵgetCurrentView"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "div")(1, "input", 63);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵlistener"]("change", function StktrfModifyComponent_div_73_Template_input_change_1_listener() {
      _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵrestoreView"](_r10);
      const ctx_r7 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"]();
      return _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵresetView"](ctx_r7.setBatchBilledQty());
    });
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("tabindex", -1);
  }
}
function StktrfModifyComponent_th_95_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "th", 64);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1, "Batch No");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
}
function StktrfModifyComponent_td_96_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "td", 65);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const row_r11 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](row_r11.batch_no);
  }
}
function StktrfModifyComponent_th_98_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "th", 66);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1, "Mfg Date");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
}
function StktrfModifyComponent_td_99_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "td", 67);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const row_r12 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](row_r12.mfg_date);
  }
}
function StktrfModifyComponent_th_101_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "th", 66);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1, "Expiry Date");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
}
function StktrfModifyComponent_td_102_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "td", 67);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const row_r13 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](row_r13.expiry_date);
  }
}
function StktrfModifyComponent_th_104_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "th", 66);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1, "Rate");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
}
function StktrfModifyComponent_td_105_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "td", 67);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const row_r14 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](row_r14.trfRate);
  }
}
function StktrfModifyComponent_th_107_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "th", 66);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1, "(IGST / SGST+CGST)");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
}
function StktrfModifyComponent_td_108_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "td", 67);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const row_r15 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate1"]("", row_r15.taxRate, " %");
  }
}
function StktrfModifyComponent_th_110_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "th", 66);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1, "Stock");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
}
function StktrfModifyComponent_td_111_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "td", 67);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const row_r16 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate1"](" ", row_r16.stock, "");
  }
}
function StktrfModifyComponent_th_113_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "th", 66);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1, "Transfer Qty");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
}
function StktrfModifyComponent_td_114_div_1_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](0, "div");
  }
}
function StktrfModifyComponent_td_114_ng_template_2_Template(rf, ctx) {
  if (rf & 1) {
    const _r17 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵgetCurrentView"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "input", 68);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtwoWayListener"]("ngModelChange", function StktrfModifyComponent_td_114_ng_template_2_Template_input_ngModelChange_0_listener($event) {
      _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵrestoreView"](_r17);
      const i_r18 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"]().index;
      const ctx_r7 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"]();
      _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtwoWayBindingSet"](ctx_r7.batchBilledQtys[i_r18], $event) || (ctx_r7.batchBilledQtys[i_r18] = $event);
      return _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵresetView"]($event);
    });
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵlistener"]("change", function StktrfModifyComponent_td_114_ng_template_2_Template_input_change_0_listener($event) {
      _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵrestoreView"](_r17);
      const i_r18 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"]().index;
      const ctx_r7 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"]();
      return _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵresetView"](ctx_r7.checkBilledQuantity($event, i_r18));
    });
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const i_r18 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"]().index;
    const ctx_r7 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtwoWayProperty"]("ngModel", ctx_r7.batchBilledQtys[i_r18]);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngModelOptions", _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵpureFunction0"](3, _c1))("disabled", ctx_r7.isDelete);
  }
}
function StktrfModifyComponent_td_114_ng_template_4_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](0);
  }
  if (rf & 2) {
    const row_r19 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"]().$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate1"](" ", row_r19.sold_qty, " ");
  }
}
function StktrfModifyComponent_td_114_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "td", 67);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](1, StktrfModifyComponent_td_114_div_1_Template, 1, 0, "div", 57)(2, StktrfModifyComponent_td_114_ng_template_2_Template, 1, 4, "ng-template", null, 1, _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplateRefExtractor"])(4, StktrfModifyComponent_td_114_ng_template_4_Template, 1, 1, "ng-template", null, 2, _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplateRefExtractor"]);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const add_r20 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵreference"](3);
    const delete_r21 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵreference"](5);
    const ctx_r7 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngIf", ctx_r7.stkAction == "AP")("ngIfThen", add_r20)("ngIfElse", delete_r21);
  }
}
function StktrfModifyComponent_th_116_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "th", 66);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1, "Value");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
}
function StktrfModifyComponent_td_117_div_1_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](0, "div");
  }
}
function StktrfModifyComponent_td_117_ng_template_2_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](0);
  }
  if (rf & 2) {
    const i_r22 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"]().index;
    const ctx_r7 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate1"](" ", ctx_r7.batchValues[i_r22], " ");
  }
}
function StktrfModifyComponent_td_117_ng_template_4_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](0);
  }
  if (rf & 2) {
    const row_r23 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"]().$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate1"](" ", row_r23.tot_val, " ");
  }
}
function StktrfModifyComponent_td_117_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "td", 67);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](1, StktrfModifyComponent_td_117_div_1_Template, 1, 0, "div", 57)(2, StktrfModifyComponent_td_117_ng_template_2_Template, 1, 1, "ng-template", null, 1, _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplateRefExtractor"])(4, StktrfModifyComponent_td_117_ng_template_4_Template, 1, 1, "ng-template", null, 2, _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplateRefExtractor"]);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const add_r24 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵreference"](3);
    const delete_r25 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵreference"](5);
    const ctx_r7 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngIf", ctx_r7.stkAction == "AP")("ngIfThen", add_r24)("ngIfElse", delete_r25);
  }
}
function StktrfModifyComponent_tr_118_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](0, "tr", 69);
  }
}
function StktrfModifyComponent_tr_119_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](0, "tr", 70);
  }
}
function StktrfModifyComponent_div_131_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](0, "div");
  }
}
function StktrfModifyComponent_ng_template_132_Template(rf, ctx) {
  if (rf & 1) {
    const _r26 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵgetCurrentView"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "button", 59);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵlistener"]("click", function StktrfModifyComponent_ng_template_132_Template_button_click_0_listener() {
      _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵrestoreView"](_r26);
      const ctx_r7 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"]();
      return _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵresetView"](ctx_r7.save());
    });
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1, " Save");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const ctx_r7 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵclassMapInterpolate1"]("mr-r-10  ", ctx_r7.constants.save_btn_class, "");
  }
}
function StktrfModifyComponent_ng_template_134_Template(rf, ctx) {
  if (rf & 1) {
    const _r27 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵgetCurrentView"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "button", 59);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵlistener"]("click", function StktrfModifyComponent_ng_template_134_Template_button_click_0_listener() {
      _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵrestoreView"](_r27);
      const ctx_r7 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"]();
      return _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵresetView"](ctx_r7.deleteConfirmation());
    });
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1, " Delete");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const ctx_r7 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵclassMapInterpolate1"]("mr-r-10  ", ctx_r7.constants.save_btn_class, "");
  }
}
function StockTrfModifyDetailComponent_tr_19_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "tr")(1, "td");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](3, "td");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](4);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](5, "td");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](6);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](7, "td", 6);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](8);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](9, "td", 6);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](10);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](11, "td", 6);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](12);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    const p_r1 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](p_r1.stockTrfNo);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](p_r1.productName);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](p_r1.batchNo);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](p_r1.soldQty);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](p_r1.rate);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"]((p_r1.soldQty * p_r1.rate).toFixed(2));
  }
}
let StktrfModifyComponent = /*#__PURE__*/(() => {
  class StktrfModifyComponent {
    stktrfService;
    medicoUtility;
    medicoService;
    fb;
    dialog;
    el;
    datePipe;
    router;
    session;
    constants = new src_app_classes_constants__WEBPACK_IMPORTED_MODULE_0__.Constants();
    recLocationList = null;
    nilGstOptionsList = new Array();
    tranTypeList = new Array();
    prodList = null;
    isSaleable = true;
    stockTypes = null;
    batchList;
    prodDisabled = false;
    enabledProdId;
    selectedStockType = new Array();
    batchIds = new Array();
    batchTrfRates = new Array();
    batchStocks = new Array();
    batchBilledQtys = new Array();
    batchFreeQtys = new Array();
    batchValues = new Array();
    detailIds = new Array();
    headerFreezed = false;
    tranTypeTemp;
    recLocationTemp;
    nilGstIndTemp;
    transNoList = null;
    maxDate;
    minDate;
    trfDate;
    stkAction;
    isDelete = false;
    paginator;
    // displayedColumns: string[] = ["BatchNo","ExpiryDate","Rate","Stock","BilledQty","FreeQty","Value"];
    displayedColumns = ["BatchNo", "MfgDate", "ExpiryDate", "Rate", "TaxRate", "Stock", "BilledQty", "Value"];
    form;
    ngOnInit() {}
    constructor(stktrfService, medicoUtility, medicoService, fb, dialog, el, datePipe, router) {
      this.stktrfService = stktrfService;
      this.medicoUtility = medicoUtility;
      this.medicoService = medicoService;
      this.fb = fb;
      this.dialog = dialog;
      this.el = el;
      this.datePipe = datePipe;
      this.router = router;
      this.form = this.fb.group({
        saveMode: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl("", _angular_forms__WEBPACK_IMPORTED_MODULE_6__.Validators.required),
        headerId: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(0),
        sendSubCompId: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        sendLocId: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        sendLocStateId: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        stockTypeDetId: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        tranType: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl("", _angular_forms__WEBPACK_IMPORTED_MODULE_6__.Validators.required),
        sendLocation: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl("", _angular_forms__WEBPACK_IMPORTED_MODULE_6__.Validators.required),
        recLocation: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl("", _angular_forms__WEBPACK_IMPORTED_MODULE_6__.Validators.required),
        trfType: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl("", _angular_forms__WEBPACK_IMPORTED_MODULE_6__.Validators.required),
        stktrfNo: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        trfDateModify: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(new Date(), _angular_forms__WEBPACK_IMPORTED_MODULE_6__.Validators.required),
        nilGstInd: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl("", _angular_forms__WEBPACK_IMPORTED_MODULE_6__.Validators.required),
        productId: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl("", _angular_forms__WEBPACK_IMPORTED_MODULE_6__.Validators.required),
        stockTypeId: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        billedQty: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl("", _angular_forms__WEBPACK_IMPORTED_MODULE_6__.Validators.required),
        freeQty: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        totalStock: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        wthStkQty: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        billableStk: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        billedStkValue: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        totalBilledQty: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        totalFreeQty: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        totalStkTrfvalue: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        batchIds: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        batchBilledQtys: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        batchFreeQtys: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        batchTrfRates: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        detailIds: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        currPeriodCode: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        currFinYear: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        empId: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        gstInd: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl("")
      });
      this.medicoService.headingName = "Stock Transfer Modify";
      this.form.get("saveMode")?.setValue(this.constants.MODIFY_MODE.toString());
      this.medicoUtility.toggleProgressBar(true);
      this.session = this.medicoUtility.getSessionVariables();
      this.medicoUtility.toggleProgressBar(false);
      this.nilGstOptionsList = [{
        key: "N",
        value: "No"
      }, {
        key: "Y",
        value: "Yes"
      }];
      this.tranTypeList = [{
        key: "85",
        value: "Dispatchable"
      }, {
        key: "86",
        value: "Non Dispatchable"
      }];
      this.form.get("nilGstInd")?.setValue(this.nilGstOptionsList[0].key);
      this.form.get("tranType")?.setValue(this.tranTypeList[0].key);
      this.maxDate = new Date();
      this.minDate = new Date(new Date().getFullYear(), new Date().getMonth(), 1);
      this.stkAction = 'AP';
      this.getDataForStkTrfModify();
    }
    getDataForStkTrfModify() {
      this.medicoUtility.toggleProgressBar(true);
      this.stktrfService.getDataForStkTrfModify(this.session.EMP_ID).subscribe(response => {
        this.form.get("sendLocation")?.setValue(response.sendLocName);
        this.form.get("sendSubCompId")?.setValue(response.sendSubCompId);
        this.form.get("sendLocId")?.setValue(response.sendLocId);
        this.form.get("sendLocStateId")?.setValue(response.sendLocStateId);
        this.recLocationList = response.recLocations;
        this.form.get("trfType")?.setValue(response.trfType);
        this.medicoUtility.toggleProgressBar(false);
      });
    }
    getTransNoList() {
      this.medicoUtility.toggleProgressBar(true);
      if (this.form.get("sendLocId")?.valid && this.form.get("recLocation")?.valid && this.form.get("trfDateModify")?.valid && this.form.get("tranType")?.valid) {
        this.trfDate = this.datePipe.transform(this.form.get("trfDateModify")?.value, "dd/MM/yyyy");
        this.stktrfService.getTransNoList(this.form.get("sendLocId")?.value, this.form.get("recLocation")?.value, this.trfDate, this.form.get("tranType")?.value).subscribe(response => {
          this.medicoUtility.toggleProgressBar(true);
          if (response.isData) {
            this.transNoList = response.transNoList;
          } else {
            this.transNoList = new Array();
            this.medicoService.popup("Message", "Invalid Transaction Number");
          }
          this.medicoUtility.toggleProgressBar(false);
        });
      } else {
        this.medicoUtility.toggleProgressBar(false);
      }
    }
    setHeaderId() {
      var num = this.form.get("stktrfNo")?.value;
      this.form.get("headerId")?.setValue(num);
    }
    addProduct() {
      this.stkAction = "AP";
      this.isDelete = false;
      this.resetDetails();
      if (this.form.get("nilGstInd")?.valid && this.form.get("recLocation")?.valid && this.form.get("tranType")?.valid) {
        if (this.form.get("tranType")?.value === '85') {
          this.isSaleable = true;
          this.medicoUtility.toggleProgressBar(true);
          this.batchList = new _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatTableDataSource();
          this.prodList = new Array();
          this.form.get("wthStkQty")?.reset();
          this.form.get("billableStk")?.reset();
          this.form.get("totalStock")?.reset();
          this.form.get('totalBilledQty')?.reset();
          this.form.get('totalFreeQty')?.reset();
          this.form.get('totalStkTrfvalue')?.reset();
          this.prodDisabled = false;
          this.enabledProdId = '';
          this.stktrfService.getAddProductListBySendRecLoc(this.form.get("sendLocId")?.value, this.form.get("sendSubCompId")?.value, this.form.get("sendLocStateId")?.value, this.form.get("nilGstInd")?.value, '', this.form.get("stktrfNo")?.value).subscribe(response => {
            this.prodList = response.prodList.splice(0);
            this.form.get('productId')?.reset();
            this.medicoUtility.toggleProgressBar(false);
          });
        } else {
          this.isSaleable = false;
          this.medicoUtility.toggleProgressBar(false);
          // this.batchList = new  MatTableDataSource<any>();
          // this.prodList=new Array();
          // this.form.get("wthStkQty")?.reset();
          // this.form.get("billableStk")?.reset();
          // this.form.get("totalStock")?.reset();
          // this.form.get('totalBilledQty')?.reset();
          // this.form.get('totalFreeQty')?.reset();
          // this.form.get('totalStkTrfvalue')?.reset();
          // this.prodDisabled=false;
          // this.enabledProdId='';
          // this.selectedStockType=this.form.get("stockTypeId")?.value;
          // this.stktrfService.getAddProductListBySendRecLoc(this.form.get("sendLocId")?.value,this.form.get("sendSubCompId")?.value,
          // this.form.get("sendLocStateId")?.value,this.form.get("nilGstInd")?.value,this.selectedStockType[0],this.form.get("stktrfNo")?.value).subscribe(response => {
          //   this.prodList=(response.prodList as Array<any>).splice(0);
          //   this.form.get('productId')?.reset();
          //   this.medicoUtility.toggleProgressBar(false);
          // });
        }
      }
    }
    deleteDetails() {
      this.stkAction = "DD";
      this.isDelete = true;
      this.form.get("stockTypeId")?.reset();
      if (this.form.get("headerId")?.value !== 0) {
        this.medicoUtility.toggleProgressBar(true);
        this.stktrfService.getSavedProdListForDelete(this.form.get("headerId")?.value, this.form.get("sendLocId")?.value).subscribe(response => {
          this.prodList = response.savedProdList.splice(0);
          this.form.get('productId')?.reset();
          this.medicoUtility.toggleProgressBar(false);
        });
      }
    }
    deleteFullTransfer() {
      this.stkAction = "DFT";
      if (this.form.get("stktrfNo")?.valid) {
        this.medicoService.confirmBox("Confirmation", "Do you want to delete Full Stock Transfer?", "Ok", "Cancel").subscribe(result => {
          if (result == true) {
            this.deleteFullTransferData();
          }
        });
      }
    }
    deleteFullTransferData() {
      this.medicoUtility.toggleProgressBar(true);
      if (this.form.get("stktrfNo")?.valid && this.form.get("headerId")?.valid) {
        this.medicoUtility.toggleProgressBar(true);
        this.form.get("saveMode")?.setValue("DELETE_TRF");
        this.form.get("batchIds")?.setValue(this.batchIds);
        this.form.get("batchBilledQtys")?.setValue(this.batchBilledQtys);
        this.form.get("batchFreeQtys")?.setValue(this.batchFreeQtys);
        this.form.get("batchTrfRates")?.setValue(this.batchTrfRates);
        this.form.get("detailIds")?.setValue(this.detailIds);
        this.form.get("stockTypeId")?.setValue('');
        this.form.get("stockTypeDetId")?.setValue(0);
        try {
          this.stktrfService.saveStkTrf(this.form).subscribe(response => {
            if (response.DATA_SAVED) {
              this.medicoService.openJustSnackBar(response.RESPONSE_MESSAGE, this.constants.snack_bar_milliseconds_8k);
              this.createNewStkTrf();
              this.medicoUtility.toggleProgressBar(false);
            } else {
              this.medicoUtility.toggleProgressBar(false);
              this.medicoService.openJustSnackBar(response.RESPONSE_MESSAGE, this.constants.snack_bar_milliseconds_8k);
            }
          }, error => {
            this.medicoUtility.toggleProgressBar(false);
            this.medicoService.openJustSnackBar("Network Error Occurred!", this.constants.snack_bar_milliseconds_8k);
          });
        } catch (error) {
          this.medicoUtility.toggleProgressBar(false);
          this.medicoService.openJustSnackBar("Network Error Occurred!", this.constants.snack_bar_milliseconds_8k);
        }
      } else {
        this.medicoUtility.toggleProgressBar(false);
      }
    }
    getAddProductListForNS() {
      if (this.form.get("stockTypeId")?.valid && this.form.get("stockTypeId")?.value !== '' && !this.isSaleable) {
        this.isSaleable = false;
        this.medicoUtility.toggleProgressBar(true);
        this.batchList = new _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatTableDataSource();
        this.prodList = new Array();
        this.form.get("wthStkQty")?.reset();
        this.form.get("billableStk")?.reset();
        this.form.get("totalStock")?.reset();
        this.form.get('totalBilledQty')?.reset();
        this.form.get('totalFreeQty')?.reset();
        this.form.get('totalStkTrfvalue')?.reset();
        this.prodDisabled = false;
        this.enabledProdId = '';
        this.selectedStockType = this.form.get("stockTypeId")?.value;
        this.stktrfService.getAddProductListBySendRecLoc(this.form.get("sendLocId")?.value, this.form.get("sendSubCompId")?.value, this.form.get("sendLocStateId")?.value, this.form.get("nilGstInd")?.value, this.selectedStockType[0], this.form.get("stktrfNo")?.value).subscribe(response => {
          this.prodList = response.prodList.splice(0);
          this.form.get('productId')?.reset();
          this.medicoUtility.toggleProgressBar(false);
        });
      }
    }
    getStockTypes() {
      if (this.form.get("tranType")?.valid && this.form.get("tranType")?.value == '86') {
        this.medicoUtility.toggleProgressBar(true);
        this.stktrfService.getStockTypes().subscribe(response => {
          this.stockTypes = response.stockTypes.splice(0);
          this.medicoUtility.toggleProgressBar(false);
        });
        this.medicoUtility.toggleProgressBar(false);
      } else {
        this.isSaleable = true;
      }
    }
    getBatchDetails() {
      this.medicoUtility.toggleProgressBar(true);
      if (this.form.get("tranType")?.valid && this.form.get("productId")?.valid) {
        this.stktrfService.setProdLock(this.form.get("productId")?.value, this.form.get("sendLocId")?.value, this.session.EMP_ID).subscribe(response => {
          if (response) {
            this.prodDisabled = true;
            this.enabledProdId = this.form.get("productId")?.value;
            this.medicoUtility.toggleProgressBar(true);
            if (this.stkAction == 'AP') {
              if (this.form.get("tranType")?.value == '85') {
                this.stktrfService.getBatchDetailsForProd(this.form.get("recLocation")?.value, this.form.get("sendLocId")?.value, this.form.get("productId")?.value, this.form.get("tranType")?.value, '').subscribe(response => {
                  this.medicoUtility.toggleProgressBar(true);
                  this.batchList = new _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatTableDataSource(response.batchList.slice(0));
                  this.batchList.paginator = this.paginator;
                  this.batchIds = response.batchIds;
                  this.batchTrfRates = response.batchTrfRates;
                  this.batchStocks = response.batchStocks;
                  this.form.get("wthStkQty")?.setValue(response.withheld_stk);
                  this.form.get("billableStk")?.setValue(response.billable_stk);
                  this.form.get("totalStock")?.setValue(response.total_stock);
                  this.medicoUtility.toggleProgressBar(false);
                });
              } else {
                this.selectedStockType = this.form.get("stockTypeId")?.value;
                this.stktrfService.getBatchDetailsForProd(this.form.get("recLocation")?.value, this.form.get("sendLocId")?.value, this.form.get("productId")?.value, this.form.get("tranType")?.value, this.selectedStockType[0]).subscribe(response => {
                  this.medicoUtility.toggleProgressBar(true);
                  this.batchList = new _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatTableDataSource(response.batchList.slice(0));
                  this.batchList.paginator = this.paginator;
                  this.batchIds = response.batchIds;
                  this.batchTrfRates = response.batchTrfRates;
                  this.batchStocks = response.batchStocks;
                  this.form.get("wthStkQty")?.setValue(response.withheld_stk);
                  this.form.get("billableStk")?.setValue(response.billable_stk);
                  this.form.get("totalStock")?.setValue(response.total_stock);
                  this.medicoUtility.toggleProgressBar(false);
                });
              }
            } else if (this.stkAction == 'DD') {
              this.stktrfService.getBatchDetailsForProdDelete(this.form.get("recLocation")?.value, this.form.get("stktrfNo")?.value, this.form.get("productId")?.value).subscribe(response => {
                this.medicoUtility.toggleProgressBar(true);
                this.batchList = new _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatTableDataSource(response.batchList.slice(0));
                this.batchList.paginator = this.paginator;
                this.batchIds = response.batchIds;
                this.batchTrfRates = response.batchTrfRates;
                this.batchStocks = response.batchStocks;
                this.detailIds = response.detailIds;
                if (this.form.get("tranType")?.value == '86') {
                  this.isSaleable = false;
                  this.form.get("stockTypeId")?.setValue(response.prodStockMap);
                  this.form.get("stockTypeDetId")?.setValue(response.prodStockMap2);
                }
                this.form.get("wthStkQty")?.setValue(response.withheld_stk);
                this.form.get("billableStk")?.setValue(response.billable_stk);
                this.form.get("totalStock")?.setValue(response.total_stock);
                this.form.get("billedQty")?.setValue(response.tot_billed);
                this.form.get("freeQty")?.setValue(response.tot_free);
                this.form.get("billedStkValue")?.setValue(response.tot_val);
                this.form.get("totalBilledQty")?.setValue(response.tot_billed);
                this.form.get("totalFreeQty")?.setValue(response.tot_free);
                this.form.get("totalStkTrfvalue")?.setValue(response.tot_val);
                this.medicoUtility.toggleProgressBar(false);
              });
            }
          } else {
            this.medicoUtility.toggleProgressBar(false);
            this.form.get('productId')?.reset();
            this.medicoService.popup("Error Message", "In use. Select any other");
          }
        });
      } else {
        this.medicoUtility.toggleProgressBar(false);
      }
    }
    resetDetails() {
      if (this.form.get("productId")?.valid) {
        this.stktrfService.releaseProdLock(this.form.get("productId")?.value, this.form.get("sendLocId")?.value, this.session.EMP_ID).subscribe(response => {});
      }
      this.form.get('productId')?.reset();
      this.form.get('billedQty')?.reset();
      this.form.get('freeQty')?.reset();
      this.form.get('totalStock')?.reset();
      this.form.get('wthStkQty')?.reset();
      this.form.get('billableStk')?.reset();
      this.form.get('billedStkValue')?.reset();
      this.form.get('totalBilledQty')?.reset();
      this.form.get('totalFreeQty')?.reset();
      this.form.get('totalStkTrfvalue')?.reset();
      this.batchList = new _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatTableDataSource();
      this.batchIds = new Array();
      this.batchTrfRates = new Array();
      this.batchStocks = new Array();
      this.batchBilledQtys = new Array();
      this.batchFreeQtys = new Array();
      this.batchValues = new Array();
      this.detailIds = new Array();
      this.prodDisabled = false;
      this.enabledProdId = '';
    }
    setBatchBilledQty() {
      if (this.form.get('billedQty')?.valid) {
        if (parseFloat(this.form.get("billedQty")?.value) > 0) {
          if (parseFloat(this.form.get("billedQty")?.value) > parseFloat(this.form.get("billableStk")?.value)) {
            this.form.get('billedQty')?.reset();
            this.medicoService.popup("Error Message", "Insufficient Stock ");
          } else {
            //console.log("batchBilledQty::"+this.batchBilledQtys);
            //console.log("batchFreeQty::"+this.batchFreeQtys);
            //console.log("batchTrfRates::"+this.batchTrfRates);
            //console.log("batchStocks::"+this.batchStocks);
            //console.log("batchIds::"+this.batchIds);
            var total = '0';
            var billed = this.form.get("billedQty")?.value;
            for (let i = 0; i < this.batchIds.length; i++) {
              if (parseFloat(this.batchStocks[i]) >= parseFloat(billed)) {
                this.batchFreeQtys[i] = '0.00';
                this.batchBilledQtys[i] = parseFloat(billed).toFixed(2);
                billed = '0.00';
                this.batchValues[i] = (parseFloat(this.batchBilledQtys[i]) * parseFloat(this.batchTrfRates[i])).toFixed(2);
                total = (parseFloat(total) + parseFloat(this.batchValues[i])).toFixed(2);
              } else {
                this.batchFreeQtys[i] = '0.00';
                this.batchBilledQtys[i] = parseFloat(this.batchStocks[i]).toFixed(2);
                billed = (parseFloat(billed) - parseFloat(this.batchStocks[i])).toFixed(2);
                this.batchValues[i] = (parseFloat(this.batchBilledQtys[i]) * parseFloat(this.batchTrfRates[i])).toFixed(2);
                total = (parseFloat(total) + parseFloat(this.batchValues[i])).toFixed(2);
              }
            }
            this.form.get("billedStkValue")?.setValue(total);
            this.form.get("totalBilledQty")?.setValue(this.form.get("billedQty")?.value);
            this.form.get("totalStkTrfvalue")?.setValue(total);
          }
        } else {
          this.form.get('billedQty')?.reset();
        }
      }
    }
    checkBilledQuantity($event, index) {
      var num = $event.target.value;
      if (parseFloat(num) >= 0) {
        $event.target.value = parseFloat(num).toFixed(2);
        var billed_qty = this.form.get("billedQty")?.value;
        var total_soldqty = '0';
        for (let i = 0; i < this.batchIds.length; i++) {
          total_soldqty = (parseFloat(total_soldqty) + parseFloat(this.batchBilledQtys[i])).toFixed(2);
          this.batchValues[index] = (parseFloat(num) * parseFloat(this.batchTrfRates[index])).toFixed(2);
        }
        if (parseFloat(total_soldqty) > parseFloat(billed_qty)) {
          $event.target.value = 0;
          this.batchValues[index] = 0;
          this.medicoService.popup("Error Message", "Quantity mismatch");
        }
      } else {
        $event.target.value = 0;
      }
    }
    save() {
      this.medicoUtility.toggleProgressBar(true);
      this.form.get("currFinYear")?.setValue(this.session.CURR_FIN_YEAR);
      this.form.get("currPeriodCode")?.setValue(this.session.CURR_PERIOD);
      this.form.get("empId")?.setValue(this.session.EMP_ID);
      this.form.get("gstInd")?.setValue(this.session.gst_ind);
      this.form.get("batchIds")?.setValue(this.batchIds);
      this.form.get("batchBilledQtys")?.setValue(this.batchBilledQtys);
      this.form.get("batchFreeQtys")?.setValue(this.batchFreeQtys);
      this.form.get("batchTrfRates")?.setValue(this.batchTrfRates);
      this.form.get("detailIds")?.setValue(this.detailIds);
      if (!this.isSaleable) {
        if (this.stkAction == 'DD') {
          // todo 
        } else {
          this.selectedStockType = this.form.get("stockTypeId")?.value;
          this.form.get("stockTypeId")?.setValue(this.selectedStockType[0]);
          this.form.get("stockTypeDetId")?.setValue(this.selectedStockType[1]);
        }
      }
      if (this.headerFreezed) {
        this.form.get("tranType")?.setValue(this.tranTypeTemp);
        this.form.get("recLocation")?.setValue(this.recLocationTemp);
        this.form.get("nilGstInd")?.setValue(this.nilGstIndTemp);
      } else {
        this.tranTypeTemp = this.form.get("tranType")?.value;
        this.recLocationTemp = this.form.get("recLocation")?.value;
        this.nilGstIndTemp = this.form.get("nilGstInd")?.value;
      }
      if (this.stkAction == 'AP') {
        this.form.get("saveMode")?.setValue(this.constants.ENTRY_MODE.toString());
      } else if (this.stkAction == 'DD') {
        this.form.get("saveMode")?.setValue(this.constants.MODIFY_MODE.toString());
      } else if (this.stkAction == 'DFT') {
        this.form.get("saveMode")?.setValue("DELETE_TRF");
      }
      if (this.form.valid) {
        this.medicoUtility.toggleProgressBar(true);
        try {
          this.stktrfService.saveStkTrf(this.form).subscribe(response => {
            if (response.DATA_SAVED) {
              if (this.stkAction == 'AP') {
                this.medicoService.openJustSnackBar(response.RESPONSE_MESSAGE, this.constants.snack_bar_milliseconds_8k);
                if (this.form.get("tranType")?.value == '85') {
                  this.prodList = this.prodList.filter(x => x.prodId !== this.form.get("productId")?.value);
                } else {
                  this.form.get("stockTypeId")?.setValue(this.selectedStockType);
                  this.prodList = this.prodList.filter(x => x.prodId !== this.form.get("productId")?.value);
                }
                this.transNoList = this.transNoList.filter(x => x.trf_id == this.form.get("stktrfNo")?.value);
                this.resetDetails();
                this.form.markAsUntouched();
                this.headerFreezed = true;
                this.medicoUtility.toggleProgressBar(false);
              } else if (this.stkAction == 'DD') {
                this.medicoService.openJustSnackBar(response.RESPONSE_MESSAGE, this.constants.snack_bar_milliseconds_8k);
                if (this.form.get("tranType")?.value == '85') {
                  this.prodList = this.prodList.filter(x => x.prodId !== this.form.get("productId")?.value);
                } else {
                  this.form.get("stockTypeId")?.setValue(this.selectedStockType);
                  this.prodList = this.prodList.filter(x => x.prodId !== this.form.get("productId")?.value);
                }
                this.transNoList = this.transNoList.filter(x => x.trf_id == this.form.get("stktrfNo")?.value);
                this.resetDetails();
                this.form.markAsUntouched();
                this.headerFreezed = true;
                this.medicoUtility.toggleProgressBar(false);
              } else if (this.stkAction == 'DFT') {
                this.medicoService.openJustSnackBar(response.RESPONSE_MESSAGE, this.constants.snack_bar_milliseconds_8k);
                if (this.form.get("tranType")?.value == '85') {
                  this.prodList = this.prodList.filter(x => x.prodId !== this.form.get("productId")?.value);
                } else {
                  this.form.get("stockTypeId")?.setValue(this.selectedStockType);
                  this.prodList = this.prodList.filter(x => x.prodId !== this.form.get("productId")?.value);
                }
                this.transNoList = this.transNoList.filter(x => x.trf_id == this.form.get("stktrfNo")?.value);
                this.resetDetails();
                this.form.markAsUntouched();
                this.headerFreezed = true;
                this.medicoUtility.toggleProgressBar(false);
              }
            } else {
              this.medicoUtility.toggleProgressBar(false);
              this.medicoService.openJustSnackBar(response.RESPONSE_MESSAGE, this.constants.snack_bar_milliseconds_8k);
            }
          }, error => {
            this.medicoUtility.toggleProgressBar(false);
            this.medicoService.openJustSnackBar("Network Error Occurred!", this.constants.snack_bar_milliseconds_8k);
          });
        } catch (error) {
          this.medicoUtility.toggleProgressBar(false);
          this.medicoService.openJustSnackBar("Network Error Occurred!", this.constants.snack_bar_milliseconds_8k);
        }
      } else {
        this.medicoUtility.toggleProgressBar(false);
      }
    }
    createNewStkTrf() {
      this.medicoUtility.toggleProgressBar(true);
      this.resetDetails();
      this.form.reset();
      this.form.markAsUntouched();
      this.form.get('productId')?.reset();
      this.form.get('billedQty')?.reset();
      this.form.get('freeQty')?.reset();
      this.form.get('totalStock')?.reset();
      this.form.get('wthStkQty')?.reset();
      this.form.get('billableStk')?.reset();
      this.form.get('billedStkValue')?.reset();
      this.form.get('totalBilledQty')?.reset();
      this.form.get('totalFreeQty')?.reset();
      this.form.get('totalStkTrfvalue')?.reset();
      this.form.get('stockTypeId')?.reset();
      this.batchList = new _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatTableDataSource();
      this.batchIds = new Array();
      this.batchTrfRates = new Array();
      this.batchStocks = new Array();
      this.batchBilledQtys = new Array();
      this.batchFreeQtys = new Array();
      this.batchValues = new Array();
      this.detailIds = new Array();
      this.prodDisabled = false;
      this.enabledProdId = '';
      this.recLocationList = new Array();
      this.nilGstOptionsList = new Array();
      this.tranTypeList = new Array();
      this.prodList = new Array();
      this.isSaleable = true;
      this.stockTypes = new Array();
      this.isDelete = false;
      this.transNoList = new Array();
      this.headerFreezed = false;
      this.tranTypeTemp = '';
      this.recLocationTemp = '';
      this.nilGstIndTemp = '';
      this.form.get("headerId")?.setValue(0);
      this.form.get("saveMode")?.setValue(this.constants.MODIFY_MODE.toString());
      this.session = this.medicoUtility.getSessionVariables();
      this.medicoUtility.toggleProgressBar(false);
      this.nilGstOptionsList = [{
        key: "N",
        value: "No"
      }, {
        key: "Y",
        value: "Yes"
      }];
      this.tranTypeList = [{
        key: "85",
        value: "Saleable"
      }, {
        key: "86",
        value: "Non Saleable"
      }];
      this.form.get("nilGstInd")?.setValue(this.nilGstOptionsList[0].key);
      this.form.get("tranType")?.setValue(this.tranTypeList[0].key);
      this.maxDate = new Date();
      this.minDate = new Date(new Date().getFullYear(), new Date().getMonth(), 1);
      this.form.get("trfDateModify")?.setValue(new Date());
      this.stkAction = 'AP';
      this.getDataForStkTrfModify();
    }
    checkDetails() {
      if (this.form.get("headerId")?.value !== 0) {
        this.medicoUtility.toggleProgressBar(true);
        this.stktrfService.getSavedProdList(this.form.get("headerId")?.value, this.form.get("sendLocId")?.value).subscribe(response => {
          if (response.savedProdList.length > 0) {
            const dialogRef = this.dialog.open(StockTrfModifyDetailComponent, {
              width: '1000px',
              data: {
                list: response.savedProdList
              }
            });
          }
          this.medicoUtility.toggleProgressBar(false);
        });
      }
    }
    clearDate(event) {
      event.stopPropagation();
      this.form.get("trfDateModify")?.setValue(null);
    }
    deleteConfirmation() {
      if (this.form.get("stktrfNo")?.valid && this.form.get("productId")?.valid) {
        if (this.prodList.length > 1) {
          this.medicoService.confirmBox("Confirmation", "Do you want to delete Item?", "Delete", "Cancel").subscribe(result => {
            if (result == true) {
              this.save();
            }
          });
        } else {
          this.medicoService.popup("Message", "Single line item in transaction. Deletion not allowed..");
        }
      }
    }
    unlockprod() {
      this.stktrfService.unlockProducts().subscribe(response => {});
      this.router.navigate(['/medico-user/home']);
    }
    static ɵfac = function StktrfModifyComponent_Factory(__ngFactoryType__) {
      return new (__ngFactoryType__ || StktrfModifyComponent)(_angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵdirectiveInject"](src_app_services_stk_trf_stktrf_service__WEBPACK_IMPORTED_MODULE_1__.StktrfService), _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵdirectiveInject"](src_app_classes_medico_utility__WEBPACK_IMPORTED_MODULE_2__.MedicoUtility), _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵdirectiveInject"](src_app_services_medico_medico_service__WEBPACK_IMPORTED_MODULE_3__.MedicoService), _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵdirectiveInject"](_angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormBuilder), _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵdirectiveInject"](_angular_material_dialog__WEBPACK_IMPORTED_MODULE_8__.MatDialog), _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵdirectiveInject"](_angular_core__WEBPACK_IMPORTED_MODULE_5__.ElementRef), _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵdirectiveInject"](_angular_common__WEBPACK_IMPORTED_MODULE_9__.DatePipe), _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵdirectiveInject"](_angular_router__WEBPACK_IMPORTED_MODULE_10__.Router));
    };
    static ɵcmp = /*@__PURE__*/_angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵdefineComponent"]({
      type: StktrfModifyComponent,
      selectors: [["app-stktrf-modify"]],
      viewQuery: function StktrfModifyComponent_Query(rf, ctx) {
        if (rf & 1) {
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵviewQuery"](_angular_material_paginator__WEBPACK_IMPORTED_MODULE_11__.MatPaginator, 5);
        }
        if (rf & 2) {
          let _t;
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵqueryRefresh"](_t = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵloadQuery"]()) && (ctx.paginator = _t.first);
        }
      },
      decls: 142,
      vars: 67,
      consts: [["trfDate", ""], ["add", ""], ["delete", ""], [3, "formGroup"], [1, "pl-1", "pr-1", "pb-1", "container"], ["fxLayout", "row", "fxLayout.lt-md", "column"], ["fxFlex", "100%"], ["fxLayout", "row", "fxLayout.lt-md", "column", 1, "pl-1", "pt-1", 2, "margin-bottom", "-12px"], ["fxFlex", "25%", "appearance", "outline", 3, "ngClass"], ["formControlName", "tranType", "matNativeControl", "", "required", "", 3, "selectionChange", "ngClass", "disabled"], [3, "value", 4, "ngFor", "ngForOf"], ["matInput", "", "type", "text", "formControlName", "sendLocation", "readonly", "", 3, "tabindex"], ["formControlName", "recLocation", "matNativeControl", "", "required", "", 3, "selectionChange", "ngClass", "disabled"], ["matInput", "", "type", "text", "formControlName", "trfType", "readonly", "", "required", "", 3, "tabindex"], ["fxLayout", "row", "fxLayout.lt-md", "column", 1, "pl-1", "pt-0", 2, "margin-bottom", "-12px"], ["fxFlex", "24.3%", "appearance", "outline", 3, "ngClass"], ["matInput", "", "placeholder", "Transfer Date", "required", "", "formControlName", "trfDateModify", "readonly", "", 1, "mat-datepicker-input", 3, "dateChange", "matDatepicker", "max", "min"], ["matDatepickerToggleIcon", "", 3, "click"], ["matSuffix", "", 3, "for", "disabled"], ["formControlName", "nilGstInd", "matNativeControl", "", "required", "", 3, "ngClass", "disabled"], ["formControlName", "stktrfNo", "matNativeControl", "", "required", "", 3, "selectionChange", "ngClass", "disabled"], ["fxLayout", "row", "fxLayout.lt-md", "column", 1, "pl-1", "pt-0", "pb-1"], ["mat-raised-button", "", "type", "button", 1, "mr-r-10", "color-3", 3, "click"], ["fxLayout", "row", "fxLayout.lt-md", "column", 1, "p-1", "pb-0"], ["fxFlex", "100%", 1, "purple-font", "bold-font", "help-title"], ["fxFlex", "24.2%", "appearance", "outline", 3, "ngClass"], ["formControlName", "stockTypeId", "matNativeControl", "", 3, "selectionChange", "ngClass", "disabled"], ["fxFlex", "49.3%", "appearance", "outline", 3, "ngClass"], ["formControlName", "productId", "matNativeControl", "", "required", "", 3, "selectionChange", "ngClass"], [3, "matTooltip", "value", "disabled", 4, "ngFor", "ngForOf"], ["fxLayout", "row", "fxLayout.lt-md", "column", 1, "pl-1", "pt-0"], [4, "ngIf"], ["matInput", "", "type", "text", "formControlName", "billableStk", "readonly", "", 1, "text-right", 3, "tabindex"], ["matInput", "", "type", "text", "formControlName", "wthStkQty", "readonly", "", 1, "text-right", 3, "tabindex"], ["matInput", "", "type", "text", "formControlName", "totalStock", "readonly", "", 1, "text-right", 3, "tabindex"], [1, "table-container", "mat-elevation-z2", "width-only-100"], ["mat-table", "", "matSort", "", 1, "table-borders-right", "width-only-100", "table-th-bg-primary", "thin-th", "mat-td-p", "mat-th-p", 3, "dataSource"], ["matColumnDef", "BatchNo"], ["mat-header-cell", "", 4, "matHeaderCellDef"], ["mat-cell", "", 4, "matCellDef"], ["matColumnDef", "MfgDate"], ["mat-header-cell", "", "class", "right-align", 4, "matHeaderCellDef"], ["mat-cell", "", "class", "right-align", 4, "matCellDef"], ["matColumnDef", "ExpiryDate"], ["matColumnDef", "Rate"], ["matColumnDef", "TaxRate"], ["matColumnDef", "Stock"], ["matColumnDef", "BilledQty"], ["matColumnDef", "Value"], ["mat-header-row", "", "mat-sort-header", "", "class", "table-row1", 4, "matHeaderRowDef", "matHeaderRowDefSticky"], ["mat-row", "", "class", "table-row1", 4, "matRowDef", "matRowDefColumns"], ["showFirstLastButtons", "", 3, "pageSizeOptions"], ["fxLayout", "row", "fxLayout.lt-md", "column", 1, "pl-1", "pt-1"], ["fxFlex", "15%", "appearance", "outline", 3, "ngClass"], ["matInput", "", "type", "text", "formControlName", "totalBilledQty", "readonly", "", 1, "text-right", 3, "tabindex"], ["matInput", "", "type", "text", "formControlName", "totalStkTrfvalue", "readonly", "", 1, "text-right", 3, "tabindex"], ["fxLayout", "row", 1, "mt-1"], [4, "ngIf", "ngIfThen", "ngIfElse"], ["mat-raised-button", "", "type", "button", 1, "mr-r-10", "color-9", 3, "click"], ["mat-raised-button", "", "type", "button", 3, "click"], [3, "value"], [3, "matTooltip", "value", "disabled"], ["appNumbersOnly", "", "step", "0.01", "matInput", "", "type", "text", "formControlName", "billedQty", "required", "", 1, "text-right", 3, "change", "tabindex"], ["appNumbersOnly", "", "step", "0.01", "readonly", "", "matInput", "", "type", "text", "formControlName", "billedQty", "required", "", 1, "text-right", 3, "change", "tabindex"], ["mat-header-cell", ""], ["mat-cell", ""], ["mat-header-cell", "", 1, "right-align"], ["mat-cell", "", 1, "right-align"], ["appNumbersOnly", "", "step", "0.01", "matInput", "", "type", "text", 1, "text-right", "mat-input-batdetails", 3, "ngModelChange", "change", "ngModel", "ngModelOptions", "disabled"], ["mat-header-row", "", "mat-sort-header", "", 1, "table-row1"], ["mat-row", "", 1, "table-row1"]],
      template: function StktrfModifyComponent_Template(rf, ctx) {
        if (rf & 1) {
          const _r1 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵgetCurrentView"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "form", 3)(1, "div", 4)(2, "div", 5)(3, "div", 6)(4, "div", 7)(5, "mat-form-field", 8)(6, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](7, "Dispatchable/Non Dispatchable");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](8, "mat-select", 9);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵlistener"]("selectionChange", function StktrfModifyComponent_Template_mat_select_selectionChange_8_listener() {
            _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵrestoreView"](_r1);
            ctx.getTransNoList();
            return _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵresetView"](ctx.getStockTypes());
          });
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](9, StktrfModifyComponent_mat_option_9_Template, 2, 2, "mat-option", 10);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](10, "mat-form-field", 8)(11, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](12, "Sending Location");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](13, "input", 11);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](14, "mat-form-field", 8)(15, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](16, "Receiving Location");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](17, "mat-select", 12);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵlistener"]("selectionChange", function StktrfModifyComponent_Template_mat_select_selectionChange_17_listener() {
            _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵrestoreView"](_r1);
            return _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵresetView"](ctx.getTransNoList());
          });
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](18, StktrfModifyComponent_mat_option_18_Template, 2, 2, "mat-option", 10);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](19, "mat-form-field", 8)(20, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](21, "Transfer Type");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](22, "input", 13);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](23, "div", 14)(24, "mat-form-field", 15)(25, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](26, "Transfer Date");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](27, "input", 16);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵlistener"]("dateChange", function StktrfModifyComponent_Template_input_dateChange_27_listener() {
            _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵrestoreView"](_r1);
            return _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵresetView"](ctx.getTransNoList());
          });
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](28, "mat-icon", 17);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵlistener"]("click", function StktrfModifyComponent_Template_mat_icon_click_28_listener($event) {
            _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵrestoreView"](_r1);
            return _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵresetView"](ctx.clearDate($event));
          });
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](29, "clear");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](30, "mat-datepicker-toggle", 18)(31, "mat-datepicker", null, 0);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](33, "mat-form-field", 15)(34, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](35, "NIL GST Products");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](36, "mat-select", 19);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](37, StktrfModifyComponent_mat_option_37_Template, 2, 2, "mat-option", 10);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](38, "mat-form-field", 15)(39, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](40, "Transfer No");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](41, "mat-select", 20);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵlistener"]("selectionChange", function StktrfModifyComponent_Template_mat_select_selectionChange_41_listener() {
            _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵrestoreView"](_r1);
            return _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵresetView"](ctx.setHeaderId());
          });
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](42, StktrfModifyComponent_mat_option_42_Template, 2, 2, "mat-option", 10);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()()();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](43, "div", 21)(44, "button", 22);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵlistener"]("click", function StktrfModifyComponent_Template_button_click_44_listener() {
            _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵrestoreView"](_r1);
            return _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵresetView"](ctx.addProduct());
          });
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](45, "Add Product");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](46, "button", 22);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵlistener"]("click", function StktrfModifyComponent_Template_button_click_46_listener() {
            _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵrestoreView"](_r1);
            return _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵresetView"](ctx.deleteDetails());
          });
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](47, "Delete Details");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](48, "button", 22);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵlistener"]("click", function StktrfModifyComponent_Template_button_click_48_listener() {
            _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵrestoreView"](_r1);
            return _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵresetView"](ctx.deleteFullTransfer());
          });
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](49, "Delete Full Transfer");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()()()();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](50, "div", 5)(51, "div", 6)(52, "div", 23)(53, "span", 24);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](54, "Transfer Order");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](55, "div", 14)(56, "mat-form-field", 25)(57, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](58, "Stock Type");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](59, "mat-select", 26);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵlistener"]("selectionChange", function StktrfModifyComponent_Template_mat_select_selectionChange_59_listener() {
            _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵrestoreView"](_r1);
            return _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵresetView"](ctx.getAddProductListForNS());
          });
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](60, StktrfModifyComponent_mat_option_60_Template, 2, 5, "mat-option", 10);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](61, "mat-form-field", 27)(62, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](63, "Product");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](64, "mat-select", 28);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵlistener"]("selectionChange", function StktrfModifyComponent_Template_mat_select_selectionChange_64_listener() {
            _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵrestoreView"](_r1);
            return _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵresetView"](ctx.getBatchDetails());
          });
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](65, StktrfModifyComponent_mat_option_65_Template, 2, 4, "mat-option", 29);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](66, "button", 22);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵlistener"]("click", function StktrfModifyComponent_Template_button_click_66_listener() {
            _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵrestoreView"](_r1);
            return _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵresetView"](ctx.resetDetails());
          });
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](67, "Reset");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](68, "div", 30)(69, "mat-form-field", 8)(70, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](71, "Quantity");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](72, StktrfModifyComponent_div_72_Template, 2, 1, "div", 31)(73, StktrfModifyComponent_div_73_Template, 2, 1, "div", 31);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](74, "mat-form-field", 8)(75, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](76, "Transfer Stock");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](77, "input", 32);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](78, "mat-form-field", 8)(79, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](80, "Withheld Stock");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](81, "input", 33);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](82, "mat-form-field", 8)(83, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](84, "Total Stock");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](85, "input", 34);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()()()();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](86, "div", 5)(87, "div", 6)(88, "div", 23)(89, "span", 24);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](90, "Batchwise Dispatch of the Transfer Order");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](91, "div", 23)(92, "div", 35)(93, "table", 36);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](94, 37);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](95, StktrfModifyComponent_th_95_Template, 2, 0, "th", 38)(96, StktrfModifyComponent_td_96_Template, 2, 1, "td", 39);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](97, 40);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](98, StktrfModifyComponent_th_98_Template, 2, 0, "th", 41)(99, StktrfModifyComponent_td_99_Template, 2, 1, "td", 42);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](100, 43);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](101, StktrfModifyComponent_th_101_Template, 2, 0, "th", 41)(102, StktrfModifyComponent_td_102_Template, 2, 1, "td", 42);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](103, 44);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](104, StktrfModifyComponent_th_104_Template, 2, 0, "th", 41)(105, StktrfModifyComponent_td_105_Template, 2, 1, "td", 42);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](106, 45);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](107, StktrfModifyComponent_th_107_Template, 2, 0, "th", 41)(108, StktrfModifyComponent_td_108_Template, 2, 1, "td", 42);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](109, 46);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](110, StktrfModifyComponent_th_110_Template, 2, 0, "th", 41)(111, StktrfModifyComponent_td_111_Template, 2, 1, "td", 42);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](112, 47);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](113, StktrfModifyComponent_th_113_Template, 2, 0, "th", 41)(114, StktrfModifyComponent_td_114_Template, 6, 3, "td", 42);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](115, 48);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](116, StktrfModifyComponent_th_116_Template, 2, 0, "th", 41)(117, StktrfModifyComponent_td_117_Template, 6, 3, "td", 42);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](118, StktrfModifyComponent_tr_118_Template, 1, 0, "tr", 49)(119, StktrfModifyComponent_tr_119_Template, 1, 0, "tr", 50);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](120, "mat-paginator", 51);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()()()();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](121, "div", 52)(122, "mat-form-field", 53)(123, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](124, "Total Transfer Qty");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](125, "input", 54);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](126, "mat-form-field", 53)(127, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](128, "Total Value");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](129, "input", 55);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](130, "div", 56);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](131, StktrfModifyComponent_div_131_Template, 1, 0, "div", 57)(132, StktrfModifyComponent_ng_template_132_Template, 2, 3, "ng-template", null, 1, _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplateRefExtractor"])(134, StktrfModifyComponent_ng_template_134_Template, 2, 3, "ng-template", null, 2, _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplateRefExtractor"]);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](136, "button", 22);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵlistener"]("click", function StktrfModifyComponent_Template_button_click_136_listener() {
            _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵrestoreView"](_r1);
            return _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵresetView"](ctx.checkDetails());
          });
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](137, "View");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](138, "button", 58);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵlistener"]("click", function StktrfModifyComponent_Template_button_click_138_listener() {
            _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵrestoreView"](_r1);
            return _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵresetView"](ctx.createNewStkTrf());
          });
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](139, "New Stock Transfer");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](140, "button", 59);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵlistener"]("click", function StktrfModifyComponent_Template_button_click_140_listener() {
            _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵrestoreView"](_r1);
            return _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵresetView"](ctx.unlockprod());
          });
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](141, "Exit");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()()()();
        }
        if (rf & 2) {
          const trfDate_r28 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵreference"](32);
          const add_r29 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵreference"](133);
          const delete_r30 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵreference"](135);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("formGroup", ctx.form);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](2);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵclassMap"](ctx.constants.page_div_class);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngClass", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngClass", "mr-r-10")("disabled", ctx.headerFreezed);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngForOf", ctx.tranTypeList);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngClass", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("tabindex", -1);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngClass", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngClass", "mr-r-10")("disabled", ctx.headerFreezed);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngForOf", ctx.recLocationList);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngClass", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("tabindex", -1);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](2);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngClass", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("matDatepicker", trfDate_r28)("max", ctx.maxDate)("min", ctx.minDate);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("for", trfDate_r28)("disabled", false);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngClass", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngClass", "mr-r-10")("disabled", ctx.headerFreezed);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngForOf", ctx.nilGstOptionsList);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngClass", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngClass", "mr-r-10")("disabled", ctx.headerFreezed);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngForOf", ctx.transNoList);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](8);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵclassMap"](ctx.constants.page_div_class);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](6);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngClass", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngClass", "mr-r-10")("disabled", ctx.isSaleable);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngForOf", ctx.stockTypes);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngClass", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngClass", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngForOf", ctx.prodList);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](4);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngClass", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngIf", ctx.stkAction == "AP");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngIf", ctx.stkAction == "DD" || ctx.stkAction == "DFT");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngClass", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("tabindex", -1);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngClass", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("tabindex", -1);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngClass", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("tabindex", -1);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵclassMap"](ctx.constants.page_div_class);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](7);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("dataSource", ctx.batchList);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](25);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("matHeaderRowDef", ctx.displayedColumns)("matHeaderRowDefSticky", true);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("matRowDefColumns", ctx.displayedColumns);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("pageSizeOptions", ctx.constants.paginator_upto_50);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](2);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngClass", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("tabindex", -1);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngClass", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("tabindex", -1);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](2);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngIf", ctx.stkAction == "AP")("ngIfThen", add_r29)("ngIfElse", delete_r30);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](9);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵclassMapInterpolate1"]("mr-r-10  ", ctx.constants.exit_btn_class, "");
        }
      },
      dependencies: [_angular_common__WEBPACK_IMPORTED_MODULE_9__.NgClass, _angular_common__WEBPACK_IMPORTED_MODULE_9__.NgForOf, _angular_common__WEBPACK_IMPORTED_MODULE_9__.NgIf, _angular_forms__WEBPACK_IMPORTED_MODULE_6__["ɵNgNoValidate"], _angular_forms__WEBPACK_IMPORTED_MODULE_6__.DefaultValueAccessor, _angular_forms__WEBPACK_IMPORTED_MODULE_6__.NgControlStatus, _angular_forms__WEBPACK_IMPORTED_MODULE_6__.NgControlStatusGroup, _angular_forms__WEBPACK_IMPORTED_MODULE_6__.RequiredValidator, _angular_forms__WEBPACK_IMPORTED_MODULE_6__.NgModel, _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormGroupDirective, _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControlName, _angular_material_form_field__WEBPACK_IMPORTED_MODULE_12__.MatFormField, _angular_material_form_field__WEBPACK_IMPORTED_MODULE_12__.MatLabel, _angular_material_form_field__WEBPACK_IMPORTED_MODULE_12__.MatSuffix, _angular_material_button__WEBPACK_IMPORTED_MODULE_13__.MatButton, _angular_flex_layout_flex__WEBPACK_IMPORTED_MODULE_14__.DefaultLayoutDirective, _angular_flex_layout_flex__WEBPACK_IMPORTED_MODULE_14__.DefaultFlexDirective, _angular_flex_layout_extended__WEBPACK_IMPORTED_MODULE_15__.DefaultClassDirective, _angular_material_input__WEBPACK_IMPORTED_MODULE_16__.MatInput, _angular_material_icon__WEBPACK_IMPORTED_MODULE_17__.MatIcon, _angular_material_select__WEBPACK_IMPORTED_MODULE_18__.MatSelect, _angular_material_core__WEBPACK_IMPORTED_MODULE_19__.MatOption, _angular_material_datepicker__WEBPACK_IMPORTED_MODULE_20__.MatDatepicker, _angular_material_datepicker__WEBPACK_IMPORTED_MODULE_20__.MatDatepickerInput, _angular_material_datepicker__WEBPACK_IMPORTED_MODULE_20__.MatDatepickerToggle, _angular_material_datepicker__WEBPACK_IMPORTED_MODULE_20__.MatDatepickerToggleIcon, _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatTable, _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatHeaderCellDef, _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatHeaderRowDef, _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatColumnDef, _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatCellDef, _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatRowDef, _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatHeaderCell, _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatCell, _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatHeaderRow, _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatRow, _angular_material_paginator__WEBPACK_IMPORTED_MODULE_11__.MatPaginator, _angular_material_tooltip__WEBPACK_IMPORTED_MODULE_21__.MatTooltip, _directives_utility_numbers_only_directive__WEBPACK_IMPORTED_MODULE_4__.NumbersOnlyDirective],
      styles: [".mat-input-batdetails[_ngcontent-%COMP%]{ width:90px !important;}\n.mat-form-field[_ngcontent-%COMP%] {width: auto !important; font-size: 17px;}\n.mat-select-value[_ngcontent-%COMP%] {max-width: 100%;width: auto;}  \n.mat-datepicker-input[_ngcontent-%COMP%] {width: 85%; }\n  \nmat-icon[_ngcontent-%COMP%] {\n    position: relative;\n    float: right;\n    top: -6px;\n    cursor: pointer;\n    color: rgba(0, 0, 0, 0.54);\n    height: 2px;\n  }\n  .table-row1[_ngcontent-%COMP%]{ height: 35px;}\n\n  .mat-form-field-can-float.mat-form-field-should-float[_ngcontent-%COMP%]   .mat-form-field-label[_ngcontent-%COMP%], \n   .mat-form-field-can-float[_ngcontent-%COMP%]   .mat-input-server[_ngcontent-%COMP%]:focus    + .mat-form-field-label-wrapper[_ngcontent-%COMP%]   .mat-form-field-label[_ngcontent-%COMP%] {\n    transform: translateY(-1.34375em) scale(1) !important; \n\n    margin-top: 0 !important;\n  }\n  .right-align[_ngcontent-%COMP%] {\n    text-align: right;\n  }\n  \n  \n/*# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbIndlYnBhY2s6Ly8uL3NyYy9hcHAvY29tcG9uZW50cy90cmFuc2FjdGlvbi9zdGt0cmYtbW9kaWZ5L3N0a3RyZi1tb2RpZnkuY29tcG9uZW50LmNzcyJdLCJuYW1lcyI6W10sIm1hcHBpbmdzIjoiQUFBQSx1QkFBdUIscUJBQXFCLENBQUM7QUFDN0MsaUJBQWlCLHNCQUFzQixFQUFFLGVBQWUsQ0FBQztBQUN6RCxtQkFBbUIsZUFBZSxDQUFDLFdBQVcsQ0FBQztBQUMvQyx1QkFBdUIsVUFBVSxFQUFFOztBQUVuQztJQUNJLGtCQUFrQjtJQUNsQixZQUFZO0lBQ1osU0FBUztJQUNULGVBQWU7SUFDZiwwQkFBMEI7SUFDMUIsV0FBVztFQUNiO0VBQ0EsYUFBYSxZQUFZLENBQUM7O0VBRTFCOztJQUVFLHFEQUFxRCxFQUFFLDJCQUEyQjtJQUNsRix3QkFBd0I7RUFDMUI7RUFDQTtJQUNFLGlCQUFpQjtFQUNuQiIsInNvdXJjZXNDb250ZW50IjpbIi5tYXQtaW5wdXQtYmF0ZGV0YWlsc3sgd2lkdGg6OTBweCAhaW1wb3J0YW50O31cclxuLm1hdC1mb3JtLWZpZWxkIHt3aWR0aDogYXV0byAhaW1wb3J0YW50OyBmb250LXNpemU6IDE3cHg7fVxyXG4ubWF0LXNlbGVjdC12YWx1ZSB7bWF4LXdpZHRoOiAxMDAlO3dpZHRoOiBhdXRvO30gIFxyXG4ubWF0LWRhdGVwaWNrZXItaW5wdXQge3dpZHRoOiA4NSU7IH1cclxuICBcclxubWF0LWljb24ge1xyXG4gICAgcG9zaXRpb246IHJlbGF0aXZlO1xyXG4gICAgZmxvYXQ6IHJpZ2h0O1xyXG4gICAgdG9wOiAtNnB4O1xyXG4gICAgY3Vyc29yOiBwb2ludGVyO1xyXG4gICAgY29sb3I6IHJnYmEoMCwgMCwgMCwgMC41NCk7XHJcbiAgICBoZWlnaHQ6IDJweDtcclxuICB9XHJcbiAgLnRhYmxlLXJvdzF7IGhlaWdodDogMzVweDt9XHJcblxyXG4gIC5tYXQtZm9ybS1maWVsZC1jYW4tZmxvYXQubWF0LWZvcm0tZmllbGQtc2hvdWxkLWZsb2F0IC5tYXQtZm9ybS1maWVsZC1sYWJlbCxcclxuICAubWF0LWZvcm0tZmllbGQtY2FuLWZsb2F0IC5tYXQtaW5wdXQtc2VydmVyOmZvY3VzICsgLm1hdC1mb3JtLWZpZWxkLWxhYmVsLXdyYXBwZXIgLm1hdC1mb3JtLWZpZWxkLWxhYmVsIHtcclxuICAgIHRyYW5zZm9ybTogdHJhbnNsYXRlWSgtMS4zNDM3NWVtKSBzY2FsZSgxKSAhaW1wb3J0YW50OyAvKiBvcmlnaW5hbGx5IHNjYWxlKDAuNzUpICovXHJcbiAgICBtYXJnaW4tdG9wOiAwICFpbXBvcnRhbnQ7XHJcbiAgfVxyXG4gIC5yaWdodC1hbGlnbiB7XHJcbiAgICB0ZXh0LWFsaWduOiByaWdodDtcclxuICB9XHJcbiAgXHJcbiAgIl0sInNvdXJjZVJvb3QiOiIifQ== */"]
    });
  }
  return StktrfModifyComponent;
})();
let StockTrfModifyDetailComponent = /*#__PURE__*/(() => {
  class StockTrfModifyDetailComponent {
    dialogRef;
    data;
    constructor(dialogRef, data) {
      this.dialogRef = dialogRef;
      this.data = data;
    }
    onNoClick() {
      this.dialogRef.close();
    }
    static ɵfac = function StockTrfModifyDetailComponent_Factory(__ngFactoryType__) {
      return new (__ngFactoryType__ || StockTrfModifyDetailComponent)(_angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵdirectiveInject"](_angular_material_dialog__WEBPACK_IMPORTED_MODULE_8__.MatDialogRef), _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵdirectiveInject"](_angular_material_dialog__WEBPACK_IMPORTED_MODULE_8__.MAT_DIALOG_DATA));
    };
    static ɵcmp = /*@__PURE__*/_angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵdefineComponent"]({
      type: StockTrfModifyDetailComponent,
      selectors: [["stk-trf-detail-dialog"]],
      decls: 23,
      vars: 1,
      consts: [["mat-dialog-title", ""], ["mat-dialog-content", ""], [1, "mytable", "table-full-width"], [4, "ngFor", "ngForOf"], ["mat-dialog-actions", "", 1, "mt-1"], ["mat-raised-button", "", "color", "primary", "mat-dialog-close", ""], ["align", "right"]],
      template: function StockTrfModifyDetailComponent_Template(rf, ctx) {
        if (rf & 1) {
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "h1", 0);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1, "Product Details");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](2, "div", 1)(3, "table", 2)(4, "thead")(5, "tr")(6, "th");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](7, "Transfer No");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](8, "th");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](9, "Product Name");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](10, "th");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](11, "Batch Number");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](12, "th");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](13, "Sold Qty");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](14, "th");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](15, "Rate");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](16, "th");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](17, "Value");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()()();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](18, "tbody");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](19, StockTrfModifyDetailComponent_tr_19_Template, 13, 6, "tr", 3);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()()();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](20, "div", 4)(21, "button", 5);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](22, "Close");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
        }
        if (rf & 2) {
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](19);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngForOf", ctx.data.list);
        }
      },
      dependencies: [_angular_common__WEBPACK_IMPORTED_MODULE_9__.NgForOf, _angular_material_button__WEBPACK_IMPORTED_MODULE_13__.MatButton, _angular_material_dialog__WEBPACK_IMPORTED_MODULE_8__.MatDialogClose, _angular_material_dialog__WEBPACK_IMPORTED_MODULE_8__.MatDialogTitle, _angular_material_dialog__WEBPACK_IMPORTED_MODULE_8__.MatDialogActions, _angular_material_dialog__WEBPACK_IMPORTED_MODULE_8__.MatDialogContent],
      encapsulation: 2
    });
  }
  return StockTrfModifyDetailComponent;
})();

/***/ }),

/***/ 56346:
/*!******************************************!*\
  !*** ./src/app/modules/stktrf.module.ts ***!
  \******************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   StktrfModule: () => (/* binding */ StktrfModule)
/* harmony export */ });
/* harmony import */ var _angular_common__WEBPACK_IMPORTED_MODULE_9__ = __webpack_require__(/*! @angular/common */ 60316);
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_8__ = __webpack_require__(/*! @angular/router */ 95072);
/* harmony import */ var src_app_guards_authentication_guard_authentication_guard__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! src/app/guards/authentication.guard/authentication.guard */ 40878);
/* harmony import */ var _child_essential_module__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./child.essential.module */ 62292);
/* harmony import */ var _directives_module__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./directives.module */ 25144);
/* harmony import */ var src_app_components_transaction_stktrf_entry_stktrf_entry_component__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! src/app/components/transaction/stktrf-entry/stktrf-entry.component */ 74022);
/* harmony import */ var src_app_components_transaction_stktrf_modify_stktrf_modify_component__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! src/app/components/transaction/stktrf-modify/stktrf-modify.component */ 89046);
/* harmony import */ var src_app_components_transaction_stktrf_lr_stktrf_lr_component__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! src/app/components/transaction/stktrf-lr/stktrf-lr.component */ 9202);
/* harmony import */ var _components_transaction_str_lr_entry_str_lr_entry_component__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! ../components/transaction/str-lr-entry/str-lr-entry.component */ 53846);
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! @angular/core */ 37580);











const routes = [{
  path: "entry",
  component: src_app_components_transaction_stktrf_entry_stktrf_entry_component__WEBPACK_IMPORTED_MODULE_3__.StktrfEntryComponent,
  canActivate: [src_app_guards_authentication_guard_authentication_guard__WEBPACK_IMPORTED_MODULE_0__.AuthenticationGuard]
}, {
  path: "modify",
  component: src_app_components_transaction_stktrf_modify_stktrf_modify_component__WEBPACK_IMPORTED_MODULE_4__.StktrfModifyComponent,
  canActivate: [src_app_guards_authentication_guard_authentication_guard__WEBPACK_IMPORTED_MODULE_0__.AuthenticationGuard]
}, {
  path: "lr",
  component: src_app_components_transaction_stktrf_lr_stktrf_lr_component__WEBPACK_IMPORTED_MODULE_5__.StktrfLrComponent,
  canActivate: [src_app_guards_authentication_guard_authentication_guard__WEBPACK_IMPORTED_MODULE_0__.AuthenticationGuard]
}];
let StktrfModule = /*#__PURE__*/(() => {
  class StktrfModule {
    static ɵfac = function StktrfModule_Factory(__ngFactoryType__) {
      return new (__ngFactoryType__ || StktrfModule)();
    };
    static ɵmod = /*@__PURE__*/_angular_core__WEBPACK_IMPORTED_MODULE_7__["ɵɵdefineNgModule"]({
      type: StktrfModule
    });
    static ɵinj = /*@__PURE__*/_angular_core__WEBPACK_IMPORTED_MODULE_7__["ɵɵdefineInjector"]({
      imports: [_angular_router__WEBPACK_IMPORTED_MODULE_8__.RouterModule.forChild(routes), _angular_common__WEBPACK_IMPORTED_MODULE_9__.CommonModule, _child_essential_module__WEBPACK_IMPORTED_MODULE_1__.ChildEssentialsModule, _directives_module__WEBPACK_IMPORTED_MODULE_2__.DirectivesModule, _angular_router__WEBPACK_IMPORTED_MODULE_8__.RouterModule]
    });
  }
  return StktrfModule;
})();
(function () {
  (typeof ngJitMode === "undefined" || ngJitMode) && _angular_core__WEBPACK_IMPORTED_MODULE_7__["ɵɵsetNgModuleScope"](StktrfModule, {
    declarations: [_components_transaction_str_lr_entry_str_lr_entry_component__WEBPACK_IMPORTED_MODULE_6__.IndividualDetailComponentStocktrfentry, _components_transaction_str_lr_entry_str_lr_entry_component__WEBPACK_IMPORTED_MODULE_6__.SummaryDetailComponentStkTrf, src_app_components_transaction_stktrf_entry_stktrf_entry_component__WEBPACK_IMPORTED_MODULE_3__.StktrfEntryComponent, src_app_components_transaction_stktrf_entry_stktrf_entry_component__WEBPACK_IMPORTED_MODULE_3__.StockTrfDetailComponent, src_app_components_transaction_stktrf_modify_stktrf_modify_component__WEBPACK_IMPORTED_MODULE_4__.StktrfModifyComponent, src_app_components_transaction_stktrf_entry_stktrf_entry_component__WEBPACK_IMPORTED_MODULE_3__.StockTrfDetailComponent, src_app_components_transaction_stktrf_modify_stktrf_modify_component__WEBPACK_IMPORTED_MODULE_4__.StockTrfModifyDetailComponent, src_app_components_transaction_stktrf_lr_stktrf_lr_component__WEBPACK_IMPORTED_MODULE_5__.StktrfLrComponent],
    imports: [_angular_router__WEBPACK_IMPORTED_MODULE_8__.RouterModule, _angular_common__WEBPACK_IMPORTED_MODULE_9__.CommonModule, _child_essential_module__WEBPACK_IMPORTED_MODULE_1__.ChildEssentialsModule, _directives_module__WEBPACK_IMPORTED_MODULE_2__.DirectivesModule],
    exports: [_angular_router__WEBPACK_IMPORTED_MODULE_8__.RouterModule]
  });
})();

/***/ })

}]);
//# sourceMappingURL=346.js.map