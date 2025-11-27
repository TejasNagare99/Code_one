"use strict";
(self["webpackChunkstk_cfa_angular"] = self["webpackChunkstk_cfa_angular"] || []).push([[57],{

/***/ 7582:
/*!***********************************************************************************!*\
  !*** ./src/app/components/approvals/stkwth-approval/stkwth-approval.component.ts ***!
  \***********************************************************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   StkwthApprovalComponent: () => (/* binding */ StkwthApprovalComponent)
/* harmony export */ });
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! @angular/forms */ 34456);
/* harmony import */ var _classes_constants__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ../../../classes/constants */ 5556);
/* harmony import */ var _angular_material_table__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! @angular/material/table */ 77697);
/* harmony import */ var _angular_material_paginator__WEBPACK_IMPORTED_MODULE_8__ = __webpack_require__(/*! @angular/material/paginator */ 24624);
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! @angular/core */ 37580);
/* harmony import */ var _services_medico_medico_service__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ../../../services/medico/medico.service */ 353);
/* harmony import */ var _services_stk_withdrawal_stk_withdrawal_service__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ../../../services/stk-withdrawal/stk-withdrawal.service */ 74303);
/* harmony import */ var _angular_material_dialog__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! @angular/material/dialog */ 12587);
/* harmony import */ var _classes_medico_utility__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ../../../classes/medico-utility */ 79955);
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_9__ = __webpack_require__(/*! @angular/router */ 95072);
/* harmony import */ var _angular_material_button__WEBPACK_IMPORTED_MODULE_10__ = __webpack_require__(/*! @angular/material/button */ 84175);
/* harmony import */ var _angular_flex_layout_flex__WEBPACK_IMPORTED_MODULE_11__ = __webpack_require__(/*! @angular/flex-layout/flex */ 91447);
/* harmony import */ var _angular_material_input__WEBPACK_IMPORTED_MODULE_12__ = __webpack_require__(/*! @angular/material/input */ 95541);
/* harmony import */ var _angular_material_checkbox__WEBPACK_IMPORTED_MODULE_13__ = __webpack_require__(/*! @angular/material/checkbox */ 97024);

















const _c0 = () => ({
  standalone: true
});
function StkwthApprovalComponent_th_9_Template(rf, ctx) {
  if (rf & 1) {
    const _r1 = _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵgetCurrentView"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](0, "th", 31)(1, "mat-checkbox", 32);
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtwoWayListener"]("ngModelChange", function StkwthApprovalComponent_th_9_Template_mat_checkbox_ngModelChange_1_listener($event) {
      _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵrestoreView"](_r1);
      const ctx_r1 = _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵnextContext"]();
      _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtwoWayBindingSet"](ctx_r1.selectAlls, $event) || (ctx_r1.selectAlls = $event);
      return _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵresetView"]($event);
    });
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵlistener"]("change", function StkwthApprovalComponent_th_9_Template_mat_checkbox_change_1_listener() {
      _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵrestoreView"](_r1);
      const ctx_r1 = _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵnextContext"]();
      return _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵresetView"](ctx_r1.selectAll());
    });
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    const ctx_r1 = _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵnextContext"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtwoWayProperty"]("ngModel", ctx_r1.selectAlls);
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵproperty"]("ngModelOptions", _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵpureFunction0"](2, _c0));
  }
}
function StkwthApprovalComponent_td_10_Template(rf, ctx) {
  if (rf & 1) {
    const _r3 = _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵgetCurrentView"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](0, "td", 33)(1, "mat-checkbox", 34);
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵlistener"]("change", function StkwthApprovalComponent_td_10_Template_mat_checkbox_change_1_listener() {
      const ctx_r3 = _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵrestoreView"](_r3);
      const row_r5 = ctx_r3.$implicit;
      const i_r6 = ctx_r3.index;
      const ctx_r1 = _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵnextContext"]();
      return _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵresetView"](ctx_r1.checkCheckBoxvalue(row_r5.swv_id, i_r6));
    });
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    const ctx_r1 = _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵnextContext"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵproperty"]("checked", ctx_r1.checkedInd);
  }
}
function StkwthApprovalComponent_th_12_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](0, "th", 35);
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtext"](1, "Challan Number");
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]();
  }
}
function StkwthApprovalComponent_td_13_Template(rf, ctx) {
  if (rf & 1) {
    const _r7 = _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵgetCurrentView"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](0, "td", 33)(1, "a", 36);
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵlistener"]("click", function StkwthApprovalComponent_td_13_Template_a_click_1_listener() {
      const row_r8 = _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵrestoreView"](_r7).$implicit;
      const ctx_r1 = _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵnextContext"]();
      return _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵresetView"](ctx_r1.onSelect(row_r8.swv_id));
    });
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](2, "u");
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtext"](3);
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]()()();
  }
  if (rf & 2) {
    const row_r8 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵadvance"](3);
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtextInterpolate"](row_r8.swv_challan_no);
  }
}
function StkwthApprovalComponent_th_15_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](0, "th", 37);
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtext"](1, "Challan Date");
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]();
  }
}
function StkwthApprovalComponent_td_16_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](0, "td", 38);
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const row_r9 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtextInterpolate"](row_r9.swv_challan_dt);
  }
}
function StkwthApprovalComponent_th_18_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](0, "th", 35);
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtext"](1, "Withdrawal Type");
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]();
  }
}
function StkwthApprovalComponent_td_19_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](0, "td", 33);
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const row_r10 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtextInterpolate"](row_r10.swv_type_name);
  }
}
function StkwthApprovalComponent_th_21_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](0, "th", 35);
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtext"](1, "Sender Name");
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]();
  }
}
function StkwthApprovalComponent_td_22_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](0, "td", 33);
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const row_r11 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtextInterpolate"](row_r11.swv_sender_name);
  }
}
function StkwthApprovalComponent_th_24_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](0, "th", 35);
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtext"](1, "State");
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]();
  }
}
function StkwthApprovalComponent_td_25_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](0, "td", 33);
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const row_r12 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtextInterpolate"](row_r12.state_name);
  }
}
function StkwthApprovalComponent_th_27_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](0, "th", 35);
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtext"](1, "Remarks ");
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]();
  }
}
function StkwthApprovalComponent_td_28_Template(rf, ctx) {
  if (rf & 1) {
    const _r13 = _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵgetCurrentView"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](0, "td", 33)(1, "div")(2, "input", 39);
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtwoWayListener"]("ngModelChange", function StkwthApprovalComponent_td_28_Template_input_ngModelChange_2_listener($event) {
      const i_r14 = _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵrestoreView"](_r13).index;
      const ctx_r1 = _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵnextContext"]();
      _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtwoWayBindingSet"](ctx_r1.remarksList[i_r14], $event) || (ctx_r1.remarksList[i_r14] = $event);
      return _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵresetView"]($event);
    });
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵlistener"]("change", function StkwthApprovalComponent_td_28_Template_input_change_2_listener($event) {
      const row_r15 = _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵrestoreView"](_r13).$implicit;
      const ctx_r1 = _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵnextContext"]();
      return _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵresetView"](ctx_r1.onChangeOfHeaderRemark($event, row_r15.swv_id));
    });
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]()()();
  }
  if (rf & 2) {
    const i_r14 = ctx.index;
    const ctx_r1 = _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵnextContext"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵadvance"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtwoWayProperty"]("ngModel", ctx_r1.remarksList[i_r14]);
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵproperty"]("ngModelOptions", _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵpureFunction0"](2, _c0));
  }
}
function StkwthApprovalComponent_tr_29_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelement"](0, "tr", 40);
  }
}
function StkwthApprovalComponent_tr_30_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelement"](0, "tr", 41);
  }
}
function StkwthApprovalComponent_th_37_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](0, "th", 35);
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtext"](1, " Item Name ");
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]();
  }
}
function StkwthApprovalComponent_td_38_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](0, "td", 33);
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const row_r16 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtextInterpolate1"](" ", row_r16.smp_prod_name, " ");
  }
}
function StkwthApprovalComponent_th_40_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](0, "th", 35);
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtext"](1, " Stock Type ");
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]();
  }
}
function StkwthApprovalComponent_td_41_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](0, "td", 33);
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const row_r17 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtextInterpolate1"](" ", row_r17.stock_type_name, " ");
  }
}
function StkwthApprovalComponent_th_43_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](0, "th", 35);
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtext"](1, " Batch No ");
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]();
  }
}
function StkwthApprovalComponent_td_44_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](0, "td", 33);
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const row_r18 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtextInterpolate1"](" ", row_r18.batch_no, " ");
  }
}
function StkwthApprovalComponent_th_46_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](0, "th", 37);
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtext"](1, " Qty ");
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]();
  }
}
function StkwthApprovalComponent_td_47_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](0, "td", 38);
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const row_r19 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtextInterpolate1"](" ", row_r19.swvdtl_disp_qty.toFixed(2), " ");
  }
}
function StkwthApprovalComponent_th_49_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](0, "th", 37);
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtext"](1, " Cases ");
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]();
  }
}
function StkwthApprovalComponent_td_50_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](0, "td", 38);
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const row_r20 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtextInterpolate1"](" ", row_r20.swvdtl_cases, " ");
  }
}
function StkwthApprovalComponent_th_52_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](0, "th", 37);
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtext"](1, " Rate ");
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]();
  }
}
function StkwthApprovalComponent_td_53_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](0, "td", 38);
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const row_r21 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtextInterpolate1"](" ", row_r21.swvdtl_rate.toFixed(2), " ");
  }
}
function StkwthApprovalComponent_tr_54_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelement"](0, "tr", 40);
  }
}
function StkwthApprovalComponent_tr_55_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelement"](0, "tr", 41);
  }
}
let StkwthApprovalComponent = /*#__PURE__*/(() => {
  class StkwthApprovalComponent {
    fb;
    medicoService;
    stkWithdrawalService;
    dialog;
    medicoUtility;
    paginator;
    session;
    constants = new _classes_constants__WEBPACK_IMPORTED_MODULE_0__.Constants();
    headerData;
    detailData;
    headerRemark;
    detailRemark;
    headerId;
    headerIds = new Array();
    checkedInd = false;
    remarkIds = new Array();
    remarksList = new Array();
    remarks = new Array();
    selectAlls = false;
    ids = new Array();
    map = new Map();
    headerColumns = ["Select", "ChallanNumber", "ChallanDate", "WithdrawalType", "SenderName", "State", "Remarks"];
    detailColumns = ["ItemName", "StockType", "BatchNo", "Qty", "Cases", "Rate"];
    form;
    constructor(fb, medicoService, stkWithdrawalService, dialog, medicoUtility) {
      this.fb = fb;
      this.medicoService = medicoService;
      this.stkWithdrawalService = stkWithdrawalService;
      this.dialog = dialog;
      this.medicoUtility = medicoUtility;
      this.form = this.fb.group({
        currPeriodCode: new _angular_forms__WEBPACK_IMPORTED_MODULE_5__.FormControl(""),
        currFinYear: new _angular_forms__WEBPACK_IMPORTED_MODULE_5__.FormControl(""),
        empId: new _angular_forms__WEBPACK_IMPORTED_MODULE_5__.FormControl(""),
        email: new _angular_forms__WEBPACK_IMPORTED_MODULE_5__.FormControl(""),
        user_name: new _angular_forms__WEBPACK_IMPORTED_MODULE_5__.FormControl(""),
        headerId: new _angular_forms__WEBPACK_IMPORTED_MODULE_5__.FormControl(0),
        headerRemarksAppr: new _angular_forms__WEBPACK_IMPORTED_MODULE_5__.FormControl(""),
        headerIds: new _angular_forms__WEBPACK_IMPORTED_MODULE_5__.FormControl(0),
        remarkIds: new _angular_forms__WEBPACK_IMPORTED_MODULE_5__.FormControl(""),
        remarksList: new _angular_forms__WEBPACK_IMPORTED_MODULE_5__.FormControl(""),
        remarkss: new _angular_forms__WEBPACK_IMPORTED_MODULE_5__.FormControl("")
      });
      this.medicoUtility.toggleProgressBar(true);
      this.session = this.medicoUtility.getSessionVariables();
      this.medicoService.headingName = "Stock Withdrawal Approval";
      this.getHeaderData();
      this.medicoUtility.toggleProgressBar(false);
    }
    ngOnInit() {}
    getHeaderData() {
      this.medicoUtility.toggleProgressBar(true);
      this.stkWithdrawalService.getSwvList(this.session.EMP_ID, this.session.USER_TYPE, '', '').subscribe(response => {
        this.ids = response.headerIds;
        this.headerData = new _angular_material_table__WEBPACK_IMPORTED_MODULE_6__.MatTableDataSource(response.stkWthList.slice(0));
        this.headerData.paginator = this.paginator;
        this.medicoUtility.toggleProgressBar(false);
      });
    }
    onSelect(id) {
      this.headerId = id;
      this.medicoUtility.toggleProgressBar(true);
      this.stkWithdrawalService.getSavedProdList(id).subscribe(response => {
        this.detailData = new _angular_material_table__WEBPACK_IMPORTED_MODULE_6__.MatTableDataSource(response.savedProdList.slice(0));
        this.detailData.paginator = this.paginator;
        this.medicoUtility.toggleProgressBar(false);
      });
    }
    onChangeOfHeaderRemark($event, id) {
      this.headerRemark = $event.target.value;
      this.map.set(id, $event.target.value);
      if (!this.remarkIds.includes(id)) {
        this.remarkIds.push(id);
      } else {
        this.remarkIds.splice(id);
      }
    }
    checkCheckBoxvalue(id, index) {
      if (this.selectAlls === true) {
        this.headerIds.splice(this.headerIds.indexOf(id), 1);
        this.remarksList.splice(index, 1);
      } else {
        if (!this.headerIds.includes(id)) {
          this.headerIds.push(id);
          this.remarksList.push("");
        } else {
          this.headerIds.splice(this.headerIds.indexOf(id), 1);
        }
        //console.log(this.headerIds);
      }
    }
    selectAll() {
      if (this.selectAlls === true) {
        this.headerIds = this.ids;
        this.checkedInd = true;
        this.remarksList.fill("");
      } else {
        this.headerIds = new Array();
        this.checkedInd = false;
        this.remarksList = new Array();
      }
    }
    approve() {
      this.form.get("currFinYear")?.setValue(this.session.CURR_FIN_YEAR);
      this.form.get("currPeriodCode")?.setValue(this.session.CURR_PERIOD);
      this.form.get("empId")?.setValue(this.session.EMP_ID);
      this.form.get("email")?.setValue(this.session.EMP_ID);
      //this.form.get("user_name").setValue(this.session.user.user_name); 
      this.form.get("headerIds")?.setValue(this.headerIds);
      this.form.get("headerRemarksAppr")?.setValue(this.map);
      this.form.get("remarkIds")?.setValue(this.remarkIds);
      this.form.get("remarkss")?.setValue(this.remarksList);
      //console.log("headerIds::"+this.headerIds)
      if (this.headerIds.length > 0) {
        this.medicoUtility.toggleProgressBar(true);
        try {
          this.stkWithdrawalService.saveStkWithdrawalSelfApproval(this.form).subscribe(response => {
            if (response.DATA_SAVED) {
              //this.medicoService.openJustSnackBar(response.RESPONSE_MESSAGE, this.constants.snack_bar_milliseconds_8k);
              this.form.reset();
              this.headerRemark = '';
              this.detailRemark = '';
              this.headerData = new _angular_material_table__WEBPACK_IMPORTED_MODULE_6__.MatTableDataSource();
              this.detailData = new _angular_material_table__WEBPACK_IMPORTED_MODULE_6__.MatTableDataSource();
              this.headerIds = new Array();
              this.checkedInd = false;
              this.remarksList = new Array();
              this.map = new Map();
              this.selectAlls = false;
              this.medicoUtility.toggleProgressBar(false);
              this.medicoService.popup("Message", response.RESPONSE_MESSAGE);
              this.getHeaderData();
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
      }
    }
    static ɵfac = function StkwthApprovalComponent_Factory(__ngFactoryType__) {
      return new (__ngFactoryType__ || StkwthApprovalComponent)(_angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵdirectiveInject"](_angular_forms__WEBPACK_IMPORTED_MODULE_5__.FormBuilder), _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵdirectiveInject"](_services_medico_medico_service__WEBPACK_IMPORTED_MODULE_1__.MedicoService), _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵdirectiveInject"](_services_stk_withdrawal_stk_withdrawal_service__WEBPACK_IMPORTED_MODULE_2__.StkWithdrawalService), _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵdirectiveInject"](_angular_material_dialog__WEBPACK_IMPORTED_MODULE_7__.MatDialog), _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵdirectiveInject"](_classes_medico_utility__WEBPACK_IMPORTED_MODULE_3__.MedicoUtility));
    };
    static ɵcmp = /*@__PURE__*/_angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵdefineComponent"]({
      type: StkwthApprovalComponent,
      selectors: [["app-stkwth-approval"]],
      viewQuery: function StkwthApprovalComponent_Query(rf, ctx) {
        if (rf & 1) {
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵviewQuery"](_angular_material_paginator__WEBPACK_IMPORTED_MODULE_8__.MatPaginator, 5);
        }
        if (rf & 2) {
          let _t;
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵqueryRefresh"](_t = _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵloadQuery"]()) && (ctx.paginator = _t.first);
        }
      },
      decls: 62,
      vars: 20,
      consts: [["autocomplete", "off", 3, "formGroup"], [1, "p-1", "container"], ["fxLayout", "row", "fxLayout.lt-md", "column"], ["fxFlex", "100%"], ["fxFlex", "100%", 1, "mt-mr-10"], [1, "table-container", "mat-elevation-z2", "width-only-100"], ["mat-table", "", "matSort", "", 1, "table-borders-right", "table-width-100", "table-height-450", "table-th-bg-primary", "table-condensed", 3, "dataSource"], ["matColumnDef", "Select", "sticky", ""], ["mat-header-cell", "", "class", "text-center", 4, "matHeaderCellDef"], ["mat-cell", "", 4, "matCellDef"], ["matColumnDef", "ChallanNumber"], ["mat-header-cell", "", 4, "matHeaderCellDef"], ["matColumnDef", "ChallanDate"], ["mat-header-cell", "", "class", "text-right", 4, "matHeaderCellDef"], ["mat-cell", "", "class", "text-right", 4, "matCellDef"], ["matColumnDef", "WithdrawalType"], ["matColumnDef", "SenderName"], ["matColumnDef", "State"], ["matColumnDef", "Remarks"], ["mat-header-row", "", "mat-sort-header", "", "class", "table-row", 4, "matHeaderRowDef", "matHeaderRowDefSticky"], ["mat-row", "", "class", "table-row", 4, "matRowDef", "matRowDefColumns"], ["showFirstLastButtons", "", 3, "pageSizeOptions"], ["matColumnDef", "ItemName"], ["matColumnDef", "StockType"], ["matColumnDef", "BatchNo"], ["matColumnDef", "Qty"], ["matColumnDef", "Cases"], ["matColumnDef", "Rate"], ["fxLayout", "row", 1, "mt-1", "mb-3"], ["mat-raised-button", "", "type", "button", 3, "click"], ["mat-raised-button", "", "type", "button", "routerLink", "/medico-user/home"], ["mat-header-cell", "", 1, "text-center"], [3, "ngModelChange", "change", "ngModel", "ngModelOptions"], ["mat-cell", ""], ["value", "row.swv_id", 3, "change", "checked"], ["mat-header-cell", ""], [1, "nav-link", "link", 3, "click"], ["mat-header-cell", "", 1, "text-right"], ["mat-cell", "", 1, "text-right"], ["type", "text", "matInput", "", 3, "ngModelChange", "change", "ngModel", "ngModelOptions"], ["mat-header-row", "", "mat-sort-header", "", 1, "table-row"], ["mat-row", "", 1, "table-row"]],
      template: function StkwthApprovalComponent_Template(rf, ctx) {
        if (rf & 1) {
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](0, "form", 0)(1, "div", 1)(2, "div", 2)(3, "div", 3)(4, "div", 2)(5, "div", 4)(6, "div", 5)(7, "table", 6);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementContainerStart"](8, 7);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtemplate"](9, StkwthApprovalComponent_th_9_Template, 2, 3, "th", 8)(10, StkwthApprovalComponent_td_10_Template, 2, 1, "td", 9);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementContainerStart"](11, 10);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtemplate"](12, StkwthApprovalComponent_th_12_Template, 2, 0, "th", 11)(13, StkwthApprovalComponent_td_13_Template, 4, 1, "td", 9);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementContainerStart"](14, 12);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtemplate"](15, StkwthApprovalComponent_th_15_Template, 2, 0, "th", 13)(16, StkwthApprovalComponent_td_16_Template, 2, 1, "td", 14);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementContainerStart"](17, 15);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtemplate"](18, StkwthApprovalComponent_th_18_Template, 2, 0, "th", 11)(19, StkwthApprovalComponent_td_19_Template, 2, 1, "td", 9);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementContainerStart"](20, 16);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtemplate"](21, StkwthApprovalComponent_th_21_Template, 2, 0, "th", 11)(22, StkwthApprovalComponent_td_22_Template, 2, 1, "td", 9);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementContainerStart"](23, 17);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtemplate"](24, StkwthApprovalComponent_th_24_Template, 2, 0, "th", 11)(25, StkwthApprovalComponent_td_25_Template, 2, 1, "td", 9);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementContainerStart"](26, 18);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtemplate"](27, StkwthApprovalComponent_th_27_Template, 2, 0, "th", 11)(28, StkwthApprovalComponent_td_28_Template, 3, 3, "td", 9);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtemplate"](29, StkwthApprovalComponent_tr_29_Template, 1, 0, "tr", 19)(30, StkwthApprovalComponent_tr_30_Template, 1, 0, "tr", 20);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelement"](31, "mat-paginator", 21);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]()()();
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](32, "div", 2)(33, "div", 4)(34, "div", 5)(35, "table", 6);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementContainerStart"](36, 22);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtemplate"](37, StkwthApprovalComponent_th_37_Template, 2, 0, "th", 11)(38, StkwthApprovalComponent_td_38_Template, 2, 1, "td", 9);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementContainerStart"](39, 23);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtemplate"](40, StkwthApprovalComponent_th_40_Template, 2, 0, "th", 11)(41, StkwthApprovalComponent_td_41_Template, 2, 1, "td", 9);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementContainerStart"](42, 24);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtemplate"](43, StkwthApprovalComponent_th_43_Template, 2, 0, "th", 11)(44, StkwthApprovalComponent_td_44_Template, 2, 1, "td", 9);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementContainerStart"](45, 25);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtemplate"](46, StkwthApprovalComponent_th_46_Template, 2, 0, "th", 13)(47, StkwthApprovalComponent_td_47_Template, 2, 1, "td", 14);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementContainerStart"](48, 26);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtemplate"](49, StkwthApprovalComponent_th_49_Template, 2, 0, "th", 13)(50, StkwthApprovalComponent_td_50_Template, 2, 1, "td", 14);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementContainerStart"](51, 27);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtemplate"](52, StkwthApprovalComponent_th_52_Template, 2, 0, "th", 13)(53, StkwthApprovalComponent_td_53_Template, 2, 1, "td", 14);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtemplate"](54, StkwthApprovalComponent_tr_54_Template, 1, 0, "tr", 19)(55, StkwthApprovalComponent_tr_55_Template, 1, 0, "tr", 20);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelement"](56, "mat-paginator", 21);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]()()()()();
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](57, "div", 28)(58, "button", 29);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵlistener"]("click", function StkwthApprovalComponent_Template_button_click_58_listener() {
            return ctx.approve();
          });
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtext"](59, "Approve");
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](60, "button", 30);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtext"](61, "Exit");
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]()()()();
        }
        if (rf & 2) {
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵproperty"]("formGroup", ctx.form);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵadvance"](2);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵclassMap"](ctx.constants.page_div_class);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵadvance"](5);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵproperty"]("dataSource", ctx.headerData);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵadvance"](22);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵproperty"]("matHeaderRowDef", ctx.headerColumns)("matHeaderRowDefSticky", true);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵproperty"]("matRowDefColumns", ctx.headerColumns);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵproperty"]("pageSizeOptions", ctx.constants.paginator_upto_1000);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵadvance"](4);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵproperty"]("dataSource", ctx.detailData);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵadvance"](19);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵproperty"]("matHeaderRowDef", ctx.detailColumns)("matHeaderRowDefSticky", true);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵproperty"]("matRowDefColumns", ctx.detailColumns);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵproperty"]("pageSizeOptions", ctx.constants.paginator_upto_25);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵadvance"](2);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵclassMapInterpolate1"]("mr-r-10 ", ctx.constants.approve_btn_class, "");
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵadvance"](2);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵclassMapInterpolate1"]("mr-r-10 ", ctx.constants.exit_btn_class, "");
        }
      },
      dependencies: [_angular_router__WEBPACK_IMPORTED_MODULE_9__.RouterLink, _angular_forms__WEBPACK_IMPORTED_MODULE_5__["ɵNgNoValidate"], _angular_forms__WEBPACK_IMPORTED_MODULE_5__.DefaultValueAccessor, _angular_forms__WEBPACK_IMPORTED_MODULE_5__.NgControlStatus, _angular_forms__WEBPACK_IMPORTED_MODULE_5__.NgControlStatusGroup, _angular_forms__WEBPACK_IMPORTED_MODULE_5__.NgModel, _angular_forms__WEBPACK_IMPORTED_MODULE_5__.FormGroupDirective, _angular_material_button__WEBPACK_IMPORTED_MODULE_10__.MatButton, _angular_flex_layout_flex__WEBPACK_IMPORTED_MODULE_11__.DefaultLayoutDirective, _angular_flex_layout_flex__WEBPACK_IMPORTED_MODULE_11__.DefaultFlexDirective, _angular_material_input__WEBPACK_IMPORTED_MODULE_12__.MatInput, _angular_material_checkbox__WEBPACK_IMPORTED_MODULE_13__.MatCheckbox, _angular_material_table__WEBPACK_IMPORTED_MODULE_6__.MatTable, _angular_material_table__WEBPACK_IMPORTED_MODULE_6__.MatHeaderCellDef, _angular_material_table__WEBPACK_IMPORTED_MODULE_6__.MatHeaderRowDef, _angular_material_table__WEBPACK_IMPORTED_MODULE_6__.MatColumnDef, _angular_material_table__WEBPACK_IMPORTED_MODULE_6__.MatCellDef, _angular_material_table__WEBPACK_IMPORTED_MODULE_6__.MatRowDef, _angular_material_table__WEBPACK_IMPORTED_MODULE_6__.MatHeaderCell, _angular_material_table__WEBPACK_IMPORTED_MODULE_6__.MatCell, _angular_material_table__WEBPACK_IMPORTED_MODULE_6__.MatHeaderRow, _angular_material_table__WEBPACK_IMPORTED_MODULE_6__.MatRow, _angular_material_paginator__WEBPACK_IMPORTED_MODULE_8__.MatPaginator],
      styles: ["/*# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IiIsInNvdXJjZVJvb3QiOiIifQ== */"]
    });
  }
  return StkwthApprovalComponent;
})();

/***/ }),

/***/ 41014:
/*!*********************************************************************************************!*\
  !*** ./src/app/components/transaction/stkwithdrawal-entry/stkwithdrawal-entry.component.ts ***!
  \*********************************************************************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   StkwithdrawalEntryComponent: () => (/* binding */ StkwithdrawalEntryComponent),
/* harmony export */   StockWthDetailComponent: () => (/* binding */ StockWthDetailComponent)
/* harmony export */ });
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! @angular/forms */ 34456);
/* harmony import */ var _classes_constants__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ../../../classes/constants */ 5556);
/* harmony import */ var _angular_material_table__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! @angular/material/table */ 77697);
/* harmony import */ var _angular_material_paginator__WEBPACK_IMPORTED_MODULE_10__ = __webpack_require__(/*! @angular/material/paginator */ 24624);
/* harmony import */ var _angular_material_dialog__WEBPACK_IMPORTED_MODULE_8__ = __webpack_require__(/*! @angular/material/dialog */ 12587);
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! @angular/core */ 37580);
/* harmony import */ var _services_stk_withdrawal_stk_withdrawal_service__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ../../../services/stk-withdrawal/stk-withdrawal.service */ 74303);
/* harmony import */ var _classes_medico_utility__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ../../../classes/medico-utility */ 79955);
/* harmony import */ var _services_medico_medico_service__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ../../../services/medico/medico.service */ 353);
/* harmony import */ var _angular_common__WEBPACK_IMPORTED_MODULE_9__ = __webpack_require__(/*! @angular/common */ 60316);
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_11__ = __webpack_require__(/*! @angular/router */ 95072);
/* harmony import */ var _angular_material_form_field__WEBPACK_IMPORTED_MODULE_12__ = __webpack_require__(/*! @angular/material/form-field */ 24950);
/* harmony import */ var _angular_material_button__WEBPACK_IMPORTED_MODULE_13__ = __webpack_require__(/*! @angular/material/button */ 84175);
/* harmony import */ var _angular_flex_layout_flex__WEBPACK_IMPORTED_MODULE_14__ = __webpack_require__(/*! @angular/flex-layout/flex */ 91447);
/* harmony import */ var _angular_flex_layout_extended__WEBPACK_IMPORTED_MODULE_15__ = __webpack_require__(/*! @angular/flex-layout/extended */ 52913);
/* harmony import */ var _angular_material_input__WEBPACK_IMPORTED_MODULE_16__ = __webpack_require__(/*! @angular/material/input */ 95541);
/* harmony import */ var _angular_material_select__WEBPACK_IMPORTED_MODULE_17__ = __webpack_require__(/*! @angular/material/select */ 25175);
/* harmony import */ var _angular_material_core__WEBPACK_IMPORTED_MODULE_18__ = __webpack_require__(/*! @angular/material/core */ 74646);
/* harmony import */ var _angular_material_datepicker__WEBPACK_IMPORTED_MODULE_19__ = __webpack_require__(/*! @angular/material/datepicker */ 61977);
/* harmony import */ var _angular_material_tooltip__WEBPACK_IMPORTED_MODULE_20__ = __webpack_require__(/*! @angular/material/tooltip */ 80640);
/* harmony import */ var _directives_utility_numbers_only_directive__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ../../../directives/utility/numbers-only.directive */ 22128);

























const _c0 = ["product"];
const _c1 = ["qtyToReduce"];
const _c2 = () => ({
  standalone: true
});
function StkwithdrawalEntryComponent_mat_option_24_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "mat-option", 62);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const s_r2 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("value", s_r2.sgprmdet_nm);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](s_r2.sgprmdet_disp_nm);
  }
}
function StkwithdrawalEntryComponent_div_31_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](0, "div");
  }
}
function StkwithdrawalEntryComponent_ng_template_32_mat_option_4_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "mat-option", 62);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const s_r5 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("value", s_r5.sup_id);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](s_r5.sup_nm);
  }
}
function StkwithdrawalEntryComponent_ng_template_32_Template(rf, ctx) {
  if (rf & 1) {
    const _r3 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵgetCurrentView"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "mat-form-field", 15)(1, "mat-label");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](2, "Name");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](3, "mat-select", 63);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵlistener"]("selectionChange", function StkwithdrawalEntryComponent_ng_template_32_Template_mat_select_selectionChange_3_listener() {
      _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵrestoreView"](_r3);
      const ctx_r3 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"]();
      return _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵresetView"](ctx_r3.getSupplierAddrById());
    });
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](4, StkwithdrawalEntryComponent_ng_template_32_mat_option_4_Template, 2, 2, "mat-option", 21);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](5, "mat-form-field", 15)(6, "mat-label");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](7, "Address 1");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](8, "input", 64);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](9, "mat-form-field", 15)(10, "mat-label");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](11, "Address 2");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](12, "input", 65);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](13, "mat-form-field", 15)(14, "mat-label");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](15, "Address 3");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](16, "input", 66);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const ctx_r3 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngClass", "mr-r-10");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](3);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngClass", "mr-r-10")("disabled", ctx_r3.headerFreezed);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngForOf", ctx_r3.supplierList);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngClass", "mr-r-10");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](3);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("tabindex", 3);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngClass", "mr-r-10");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](3);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("tabindex", 4);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngClass", "mr-r-10");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](3);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("tabindex", 5);
  }
}
function StkwithdrawalEntryComponent_ng_template_34_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "mat-form-field", 15)(1, "mat-label");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](2, "Name");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](3, "input", 67);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](4, "mat-form-field", 15)(5, "mat-label");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](6, "Address 1");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](7, "input", 68, 5);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](9, "mat-form-field", 15)(10, "mat-label");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](11, "Address 2");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](12, "input", 69, 6);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](14, "mat-form-field", 15)(15, "mat-label");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](16, "Address 3");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](17, "input", 70, 7);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngClass", "mr-r-10");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](3);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("tabindex", 2);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngClass", "mr-r-10");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](3);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("tabindex", 3);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngClass", "mr-r-10");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](3);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("tabindex", 4);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngClass", "mr-r-10");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](3);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("tabindex", 5);
  }
}
function StkwithdrawalEntryComponent_mat_option_41_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "mat-option", 62);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const s_r6 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("value", s_r6.sg_prmdet_id);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](s_r6.sgprmdet_disp_nm);
  }
}
function StkwithdrawalEntryComponent_mat_option_82_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "mat-option", 71);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const p_r7 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵpropertyInterpolate"]("matTooltip", p_r7.smp_prod_name);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("value", p_r7.smp_prod_id);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵattribute"]("data-divId", p_r7.smp_std_div_id);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](p_r7.smp_prod_name);
  }
}
function StkwithdrawalEntryComponent_mat_option_87_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "mat-option", 62);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const s_r8 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("value", s_r8.sgprmdet_nm);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](s_r8.sgprmdet_disp_nm);
  }
}
function StkwithdrawalEntryComponent_th_91_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "th", 72);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1, "Batch No");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
}
function StkwithdrawalEntryComponent_td_92_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "td", 73);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const row_r9 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](row_r9.batch_no);
  }
}
function StkwithdrawalEntryComponent_th_94_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "th", 74);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1, "Stock");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
}
function StkwithdrawalEntryComponent_td_95_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "td", 75);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const row_r10 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](row_r10.stock.toFixed(2));
  }
}
function StkwithdrawalEntryComponent_th_97_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "th", 74);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1, "Mfg Date");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
}
function StkwithdrawalEntryComponent_td_98_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "td", 75);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const row_r11 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](row_r11.mfg_dt);
  }
}
function StkwithdrawalEntryComponent_th_100_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "th", 74);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1, "Expiry");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
}
function StkwithdrawalEntryComponent_td_101_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "td", 75);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const row_r12 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](row_r12.exp_dt);
  }
}
function StkwithdrawalEntryComponent_th_103_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "th", 74);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1, "Qty Withdrawn");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
}
function StkwithdrawalEntryComponent_td_104_Template(rf, ctx) {
  if (rf & 1) {
    const _r13 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵgetCurrentView"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "td", 75)(1, "input", 76, 8);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtwoWayListener"]("ngModelChange", function StkwithdrawalEntryComponent_td_104_Template_input_ngModelChange_1_listener($event) {
      const i_r14 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵrestoreView"](_r13).index;
      const ctx_r3 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"]();
      _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtwoWayBindingSet"](ctx_r3.batchWthQtys[i_r14], $event) || (ctx_r3.batchWthQtys[i_r14] = $event);
      return _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵresetView"]($event);
    });
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵlistener"]("change", function StkwithdrawalEntryComponent_td_104_Template_input_change_1_listener($event) {
      const i_r14 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵrestoreView"](_r13).index;
      const ctx_r3 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"]();
      return _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵresetView"](ctx_r3.setRemainingStock($event, i_r14));
    });
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    const i_r14 = ctx.index;
    const ctx_r3 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtwoWayProperty"]("ngModel", ctx_r3.batchWthQtys[i_r14]);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngModelOptions", _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵpureFunction0"](2, _c2));
  }
}
function StkwithdrawalEntryComponent_th_106_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "th", 74);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1, "Cases");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
}
function StkwithdrawalEntryComponent_td_107_Template(rf, ctx) {
  if (rf & 1) {
    const _r15 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵgetCurrentView"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "td", 75)(1, "input", 77);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtwoWayListener"]("ngModelChange", function StkwithdrawalEntryComponent_td_107_Template_input_ngModelChange_1_listener($event) {
      const i_r16 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵrestoreView"](_r15).index;
      const ctx_r3 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"]();
      _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtwoWayBindingSet"](ctx_r3.batchCases[i_r16], $event) || (ctx_r3.batchCases[i_r16] = $event);
      return _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵresetView"]($event);
    });
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    const i_r16 = ctx.index;
    const ctx_r3 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtwoWayProperty"]("ngModel", ctx_r3.batchCases[i_r16]);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngModelOptions", _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵpureFunction0"](2, _c2));
  }
}
function StkwithdrawalEntryComponent_th_109_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "th", 74);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1, "Rate");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
}
function StkwithdrawalEntryComponent_td_110_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "td", 75);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const row_r17 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](row_r17.rate.toFixed(2));
  }
}
function StkwithdrawalEntryComponent_th_112_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "th", 74);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1, "Stock After Withdrawal");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
}
function StkwithdrawalEntryComponent_td_113_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "td", 75);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const i_r18 = ctx.index;
    const ctx_r3 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate1"](" ", ctx_r3.stkQtyAfterWth[i_r18], " ");
  }
}
function StkwithdrawalEntryComponent_tr_114_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](0, "tr", 78);
  }
}
function StkwithdrawalEntryComponent_tr_115_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](0, "tr", 79);
  }
}
function StkwithdrawalEntryComponent_div_128_Template(rf, ctx) {
  if (rf & 1) {
    const _r19 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵgetCurrentView"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "div")(1, "button", 58);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵlistener"]("click", function StkwithdrawalEntryComponent_div_128_Template_button_click_1_listener() {
      _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵrestoreView"](_r19);
      const fileInput_r20 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵreference"](5);
      return _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵresetView"](fileInput_r20.click());
    });
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](2, "span");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](3, "Choose Files");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](4, "input", 80, 9);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵlistener"]("change", function StkwithdrawalEntryComponent_div_128_Template_input_change_4_listener($event) {
      _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵrestoreView"](_r19);
      const ctx_r3 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"]();
      return _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵresetView"](ctx_r3.handleFileUpload($event));
    });
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()()();
  }
}
function StockWthDetailComponent_tr_19_Template(rf, ctx) {
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
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](p_r1.smp_prod_name);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](p_r1.stock_type_name);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](p_r1.batch_no);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](p_r1.swvdtl_disp_qty.toFixed(2));
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](p_r1.swvdtl_cases);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](p_r1.swvdtl_rate.toFixed(2));
  }
}
let StkwithdrawalEntryComponent = /*#__PURE__*/(() => {
  class StkwithdrawalEntryComponent {
    stkWithdrawalService;
    medicoUtility;
    medicoService;
    fb;
    dialog;
    el;
    datepipe;
    session;
    constants = new _classes_constants__WEBPACK_IMPORTED_MODULE_0__.Constants();
    maxDate;
    minDate;
    stkWithTypeList = null;
    stockTypes = null;
    stateList = new Array();
    prodList = null;
    showSupplierList = false;
    supplierList = null;
    batchList;
    batchStocks = new Array();
    batchIds = new Array();
    detailIds = new Array();
    rates = new Array();
    batchWthQtys = new Array();
    batchCases = new Array();
    stkQtyAfterWth = new Array();
    headerFreezed = false;
    stock_type;
    filelist;
    file;
    formData = new FormData();
    showUpload = false;
    qtyToReduce;
    product;
    paginator;
    displayedColumns = ["BatchNo", "Stock", "MfgDate", "Expiry", "qty", "cases", "rate", "stkAfterWth"];
    form;
    constructor(stkWithdrawalService, medicoUtility, medicoService, fb, dialog, el, datepipe) {
      this.stkWithdrawalService = stkWithdrawalService;
      this.medicoUtility = medicoUtility;
      this.medicoService = medicoService;
      this.fb = fb;
      this.dialog = dialog;
      this.el = el;
      this.datepipe = datepipe;
      this.form = this.fb.group({
        headerId: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(0),
        action: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl("S"),
        detailId: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(0),
        divId: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(0),
        currPeriodCode: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        currFinYear: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        empId: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        sendSubCompId: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        sendLocId: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        receipt_datetime: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        stkWithdrawalNo: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        period: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        stkWithdrawalDate: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(new Date(), _angular_forms__WEBPACK_IMPORTED_MODULE_6__.Validators.required),
        stkWithdrawalType: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl("", _angular_forms__WEBPACK_IMPORTED_MODULE_6__.Validators.required),
        senderId: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        senderName: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl("", _angular_forms__WEBPACK_IMPORTED_MODULE_6__.Validators.required),
        address1: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl("", _angular_forms__WEBPACK_IMPORTED_MODULE_6__.Validators.required),
        address2: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl("", _angular_forms__WEBPACK_IMPORTED_MODULE_6__.Validators.required),
        address3: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl("", _angular_forms__WEBPACK_IMPORTED_MODULE_6__.Validators.required),
        destination: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        transporter: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        lrnumber: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        lrdate: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        noOfCases: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(0),
        stateId: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl("", _angular_forms__WEBPACK_IMPORTED_MODULE_6__.Validators.required),
        displayMsg: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        remarks: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        productId: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl("", _angular_forms__WEBPACK_IMPORTED_MODULE_6__.Validators.required),
        stockType: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl("", _angular_forms__WEBPACK_IMPORTED_MODULE_6__.Validators.required),
        batchIds: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        detailIds: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        batchStocks: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        rates: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        batchWthQtys: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        batchCases: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        stkQtyAfterWth: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        nstkWithdrawalDate: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(),
        nlrdate: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        stkWthFiles: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl("")
      });
      this.medicoService.headingName = "Stock Withdrawal Entry";
      this.medicoUtility.toggleProgressBar(true);
      this.session = this.medicoUtility.getSessionVariables();
      this.maxDate = new Date();
      this.minDate = new Date(new Date().getFullYear(), new Date().getMonth(), 1);
      this.medicoUtility.toggleProgressBar(false);
      this.getDataForStkWithdrawalEntry();
    }
    ngOnInit() {}
    clearDate(event) {
      event.stopPropagation();
      this.form.get("stkWithdrawalDate")?.setValue(null);
    }
    getDataForStkWithdrawalEntry() {
      this.medicoUtility.toggleProgressBar(true);
      this.stkWithdrawalService.getDataForStkWthEntry().subscribe(response => {
        this.form.get("sendSubCompId")?.setValue(response.sendSubCompId);
        this.form.get("sendLocId")?.setValue(response.sendLocId);
        //this.form.get("stkWithdrawalDate")?.setValue(response.trnDateStkWth);
        this.form.get("receipt_datetime")?.setValue(response.receipt_datetime);
        this.form.get("period")?.setValue(response.period_alt_name);
        this.stkWithTypeList = response.stkWthTypeList;
        this.stockTypes = response.stockTypes;
        this.stateList = response.stateList;
        this.prodList = response.prodList;
        this.medicoUtility.toggleProgressBar(false);
      });
    }
    getSupplierByType() {
      if (this.form.get("stkWithdrawalType")?.valid) {
        this.form.get("senderId")?.setValue('');
        this.form.get("senderName")?.setValue('');
        this.form.get("address1")?.setValue('');
        this.form.get("address2")?.setValue('');
        this.form.get("address3")?.setValue('');
        if (this.form.get("stkWithdrawalType")?.value !== '99') {
          this.showSupplierList = true;
          this.stkWithdrawalService.getSupplierByType(this.form.get("stkWithdrawalType")?.value, this.form.get("sendSubCompId")?.value).subscribe(response => {
            this.supplierList = response.supplierList;
          });
        } else {
          this.showSupplierList = false;
        }
      }
    }
    getSupplierAddrById() {
      if (this.form.get("senderId")?.valid) {
        this.medicoUtility.toggleProgressBar(true);
        this.stkWithdrawalService.getSupplierAddrById(this.form.get("senderId")?.value).subscribe(response => {
          this.form.get("senderName")?.setValue(response.supplier.sup_nm);
          this.form.get("address1")?.setValue(response.supplier.sup_address1);
          this.form.get("address2")?.setValue(response.supplier.sup_address2);
          this.form.get("address3")?.setValue(response.supplier.sup_address3);
          this.stateList = this.stateList?.filter(s => s.sg_prmdet_id == response.supplier.state_id);
          this.form.get("stateId")?.setValue(response.supplier.state_id);
          this.medicoUtility.toggleProgressBar(false);
        });
      }
    }
    getBatchDetailsByProdStkType(type) {
      this.batchList = new _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatTableDataSource();
      this.batchIds = new Array();
      this.batchStocks = new Array();
      this.detailIds = new Array();
      this.rates = new Array();
      this.batchWthQtys = new Array();
      this.batchCases = new Array();
      this.stkQtyAfterWth = new Array();
      if (type == 'P' && this.prodList) {
        for (let i = 0; i < this.prodList.length; i++) {
          if (this.form.get("productId")?.value == this.prodList[i].smp_prod_id) {
            this.stock_type = this.prodList[i].storage_type;
            this.form.get("stockType")?.setValue(this.stock_type.trim());
            //console.log("stock_type ::"+this.form.get("stockType")?.value);
          }
        }
      }
      if (this.form.get("productId")?.valid && this.form.get("stockType")?.valid) {
        this.medicoUtility.toggleProgressBar(true);
        this.stkWithdrawalService.getBatchDetailsByProdStkType(this.form.get("productId")?.value, this.form.get("stockType")?.value, this.form.get("sendLocId")?.value, this.form.get("headerId")?.value, this.form.get("action")?.value).subscribe(response => {
          this.batchList = response.batchList;
          this.batchStocks = response.batchStocks;
          this.batchIds = response.batchIds;
          this.detailIds = response.detailIds;
          this.rates = response.rates;
          // this.stkQtyAfterWth=response.batchStocks;
          this.form.get("divId")?.setValue(response.divId);
          for (let i = 0; i < this.batchIds.length; i++) {
            this.batchWthQtys[i] = parseFloat('0').toFixed(2);
            this.batchCases[i] = parseFloat('0').toFixed(2);
            this.stkQtyAfterWth[i] = parseFloat(response.batchStocks[i]).toFixed(2);
          }
          // this.qtyToReduce.first.nativeElement.focus();
          this.medicoUtility.toggleProgressBar(false);
        });
      }
    }
    setRemainingStock($event, index) {
      var num = $event.target.value;
      if (parseFloat(num) < 0) {
        $event.target.value = 0;
        this.medicoService.popup("Error Message", "Negative Qty not allowed");
      } else {
        if (parseFloat(num) > parseFloat(this.batchStocks[index])) {
          $event.target.value = 0;
          this.medicoService.popup("Error Message", "Insufficient stock");
        } else {
          this.stkQtyAfterWth[index] = (parseFloat(this.batchStocks[index]) - parseFloat(num)).toFixed(2);
        }
      }
    }
    handleFileUpload(event) {
      this.filelist = event.target.files;
      if (this.filelist && this.filelist.length == 2) {
        this.file = this.filelist[0];
        this.formData = new FormData();
        this.formData.append('file', this.file);
        this.formData.append('file2', this.filelist[1]);
        this.formData.append('headerId', this.form.get("headerId")?.value);
        this.medicoUtility.toggleProgressBar(true);
        this.stkWithdrawalService.stkWthFileUpload(this.formData).subscribe(result => {
          this.medicoService.popup("Message", result.RESPONSE_MESSAGE);
          this.filelist = null;
          this.file = null;
          this.formData = new FormData();
          this.medicoUtility.toggleProgressBar(false);
        }, error => {
          this.filelist = null;
          this.file = null;
          this.formData = new FormData();
          this.medicoService.popup("Message", "Network Error Occurred");
          this.medicoUtility.toggleProgressBar(false);
        });
      } else {
        this.medicoService.popup("Message", "You can select only two");
      }
    }
    save() {
      var flag = '1';
      for (let i = 0; i < this.batchWthQtys.length; i++) {
        if (parseFloat(this.batchWthQtys[i]) > 0) {
          flag = '0';
          break;
        }
      }
      if (flag == '1') {
        this.medicoService.popup("Error Message", "Withdrawn Qty selection is mandatory");
        return false;
      }
      this.form.get("currFinYear")?.setValue(this.session.CURR_FIN_YEAR);
      this.form.get("currPeriodCode")?.setValue(this.session.CURR_PERIOD);
      this.form.get("empId")?.setValue(this.session.EMP_ID);
      this.form.get("batchIds")?.setValue(this.batchIds);
      this.form.get("detailIds")?.setValue(this.detailIds);
      this.form.get("batchStocks")?.setValue(this.batchStocks);
      this.form.get("rates")?.setValue(this.rates);
      this.form.get("batchWthQtys")?.setValue(this.batchWthQtys);
      this.form.get("batchCases")?.setValue(this.batchCases);
      this.form.get("stkQtyAfterWth")?.setValue(this.stkQtyAfterWth);
      if (this.form.get("headerId")?.value == 0) {
        let lrDate = this.datepipe.transform(this.form.get("lrdate")?.value, 'dd/MM/yyyy');
        let trnDate = this.datepipe.transform(this.form.get("stkWithdrawalDate")?.value, 'dd/MM/yyyy');
        this.form.get("nlrdate")?.setValue(lrDate);
        this.form.get("nstkWithdrawalDate")?.setValue(trnDate);
      }
      if (this.form.valid) {
        this.medicoUtility.toggleProgressBar(true);
        try {
          this.stkWithdrawalService.saveStkWithdrawal(this.form).subscribe(response => {
            //this.medicoService.openJustSnackBar(response.RESPONSE_MESSAGE, this.constants.snack_bar_milliseconds_8k);
            this.form.get("headerId")?.setValue(Number(response.headerId));
            if (!this.headerFreezed) {
              this.form.get("stkWithdrawalNo")?.setValue(response.stkWithDrawalNo);
            }
            this.showUpload = true;
            this.reset();
            this.form.controls['senderName'].disable();
            this.form.controls['address1'].disable();
            this.form.controls['address2'].disable();
            this.form.controls['address3'].disable();
            this.form.controls['destination'].disable();
            this.form.controls['transporter'].disable();
            this.form.controls['lrnumber'].disable();
            this.form.controls['noOfCases'].disable();
            this.form.controls['displayMsg'].disable();
            this.form.controls['remarks'].disable();
            this.headerFreezed = true;
            this.medicoService.popup("Message", response.RESPONSE_MESSAGE);
            this.medicoUtility.toggleProgressBar(false);
            this.product.nativeElement.focus();
          }, error => {
            this.medicoUtility.toggleProgressBar(false);
            this.medicoService.openJustSnackBar("Network Error Occurred!", this.constants.snack_bar_milliseconds_8k);
          });
        } catch (error) {
          this.medicoUtility.toggleProgressBar(false);
          this.medicoService.popup("Stock Withdrawal", "Network Error Occurred!");
        }
      } else {
        this.medicoUtility.validateAllFields(this.form);
        this.medicoUtility.toggleProgressBar(false);
      }
    }
    reset() {
      this.form.get('productId')?.reset();
      this.form.get('stockType')?.reset();
      this.batchList = new _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatTableDataSource();
      this.batchIds = new Array();
      this.batchStocks = new Array();
      this.detailIds = new Array();
      this.rates = new Array();
      this.batchWthQtys = new Array();
      this.batchCases = new Array();
      this.stkQtyAfterWth = new Array();
    }
    createNewWithdrawal() {
      window.open(this.constants.SERVER_SERVLET_CONTEXT_NAME + "/stkwithdrawal/entry", "_self");
    }
    checkDetails() {
      if (this.form.get("headerId")?.value !== 0) {
        this.medicoUtility.toggleProgressBar(true);
        this.stkWithdrawalService.getSavedProdList(this.form.get("headerId")?.value).subscribe(response => {
          if (response.savedProdList.length > 0) {
            const dialogRef = this.dialog.open(StockWthDetailComponent, {
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
    static ɵfac = function StkwithdrawalEntryComponent_Factory(__ngFactoryType__) {
      return new (__ngFactoryType__ || StkwithdrawalEntryComponent)(_angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵdirectiveInject"](_services_stk_withdrawal_stk_withdrawal_service__WEBPACK_IMPORTED_MODULE_1__.StkWithdrawalService), _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵdirectiveInject"](_classes_medico_utility__WEBPACK_IMPORTED_MODULE_2__.MedicoUtility), _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵdirectiveInject"](_services_medico_medico_service__WEBPACK_IMPORTED_MODULE_3__.MedicoService), _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵdirectiveInject"](_angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormBuilder), _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵdirectiveInject"](_angular_material_dialog__WEBPACK_IMPORTED_MODULE_8__.MatDialog), _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵdirectiveInject"](_angular_core__WEBPACK_IMPORTED_MODULE_5__.ElementRef), _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵdirectiveInject"](_angular_common__WEBPACK_IMPORTED_MODULE_9__.DatePipe));
    };
    static ɵcmp = /*@__PURE__*/_angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵdefineComponent"]({
      type: StkwithdrawalEntryComponent,
      selectors: [["app-stkwithdrawal-entry"]],
      viewQuery: function StkwithdrawalEntryComponent_Query(rf, ctx) {
        if (rf & 1) {
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵviewQuery"](_c0, 5);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵviewQuery"](_angular_material_paginator__WEBPACK_IMPORTED_MODULE_10__.MatPaginator, 5);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵviewQuery"](_c1, 5);
        }
        if (rf & 2) {
          let _t;
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵqueryRefresh"](_t = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵloadQuery"]()) && (ctx.product = _t.first);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵqueryRefresh"](_t = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵloadQuery"]()) && (ctx.paginator = _t.first);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵqueryRefresh"](_t = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵloadQuery"]()) && (ctx.qtyToReduce = _t);
        }
      },
      decls: 129,
      vars: 70,
      consts: [["stkWithdrawalDate", ""], ["content", ""], ["other_content", ""], ["lrdate", ""], ["product", ""], ["address1", ""], ["address2", ""], ["address3", ""], ["qtyToReduce", ""], ["fileInput", ""], [3, "formGroup"], [1, "pl-1", "pr-1", "pb-1", "container"], ["fxLayout", "row", "fxLayout.lt-md", "column"], ["fxFlex", "100%"], ["fxLayout", "row", "fxLayout.lt-md", "column", 1, "pl-1", "pt-1"], ["fxFlex", "25%", "appearance", "outline", 3, "ngClass"], ["matInput", "", "type", "text", "formControlName", "stkWithdrawalNo", "readonly", "", 3, "tabindex"], ["matInput", "", "type", "text", "formControlName", "period", "readonly", "", 3, "tabindex"], ["matInput", "", "placeholder", "Withdrawal Date", "required", "", "formControlName", "stkWithdrawalDate", "readonly", "", 1, "mat-datepicker-input", 3, "matDatepicker", "max", "min"], ["matSuffix", "", 3, "for", "disabled"], ["cdkFocusInitial", "", "tabindex", "1", "formControlName", "stkWithdrawalType", "matNativeControl", "", "required", "", 3, "selectionChange", "ngClass", "disabled"], [3, "value", 4, "ngFor", "ngForOf"], ["fxLayout", "row", "fxLayout.lt-md", "column", 1, "p-1", "pb-0"], ["fxFlex", "100%", 1, "purple-font", "bold-font", "help-title"], ["fxLayout", "row", "fxLayout.lt-md", "column", 1, "pl-1", "pt-0"], [4, "ngIf", "ngIfThen", "ngIfElse"], ["tabindex", "11", "formControlName", "stateId", "matNativeControl", "", "required", "", 3, "ngClass", "disabled"], ["matInput", "", "type", "text", "formControlName", "destination", 3, "tabindex"], ["matInput", "", "type", "text", "formControlName", "transporter", 3, "tabindex"], ["tabindex", "5", "appNumbersOnly", "", "step", "0.01", "matInput", "", "type", "text", "formControlName", "noOfCases", 1, "text-right", 3, "tabindex"], ["matInput", "", "type", "text", "formControlName", "lrnumber", 3, "tabindex"], ["matInput", "", "placeholder", "LR Date", "formControlName", "lrdate", "readonly", "", 1, "mat-datepicker-input", 3, "matDatepicker", "max"], ["matSuffix", "", 3, "for", "disabled", "tabindex"], ["matInput", "", "type", "text", "formControlName", "displayMsg", 3, "tabindex"], ["matInput", "", "type", "text", "formControlName", "remarks", 3, "tabindex"], ["fxFlex", "50%", "appearance", "outline", 3, "ngClass"], ["tabindex", "14", "formControlName", "productId", "matNativeControl", "", "required", "", 3, "selectionChange", "ngClass"], [3, "matTooltip", "value", 4, "ngFor", "ngForOf"], ["tabindex", "15", "formControlName", "stockType", "matNativeControl", "", "required", "", 3, "selectionChange", "ngClass"], [1, "table-container", "mat-elevation-z2", "width-only-100"], ["mat-table", "", "matSort", "", 1, "table-borders-right", "width-only-100", "table-th-bg-primary", "thin-th", "mat-td-p", "mat-th-p", 3, "dataSource"], ["matColumnDef", "BatchNo"], ["mat-header-cell", "", 4, "matHeaderCellDef"], ["mat-cell", "", 4, "matCellDef"], ["matColumnDef", "Stock"], ["mat-header-cell", "", "class", "text-right", 4, "matHeaderCellDef"], ["mat-cell", "", "class", "text-right", 4, "matCellDef"], ["matColumnDef", "MfgDate"], ["matColumnDef", "Expiry"], ["matColumnDef", "qty"], ["matColumnDef", "cases"], ["matColumnDef", "rate"], ["matColumnDef", "stkAfterWth"], ["mat-header-row", "", "mat-sort-header", "", "class", "table-row1", 4, "matHeaderRowDef", "matHeaderRowDefSticky"], ["mat-row", "", "class", "table-row1", 4, "matRowDef", "matRowDefColumns"], ["showFirstLastButtons", "", 3, "pageSizeOptions"], ["fxLayout", "row", 1, "mt-1"], ["mat-raised-button", "", "type", "button", 3, "click"], ["mat-raised-button", "", "type", "button", 1, "mr-r-10", "color-4", 3, "click"], ["mat-raised-button", "", "type", "button", 1, "mr-r-10", "color-9", 3, "click"], ["mat-raised-button", "", "type", "button", "routerLink", "/medico-user/home"], [4, "ngIf"], [3, "value"], ["tabindex", "2", "formControlName", "senderId", "matNativeControl", "", "required", "", 3, "selectionChange", "ngClass", "disabled"], ["matInput", "", "type", "text", "formControlName", "address1", "readonly", "", "required", "", 3, "tabindex"], ["matInput", "", "type", "text", "formControlName", "address2", "readonly", "", "required", "", 3, "tabindex"], ["matInput", "", "type", "text", "formControlName", "address3", "readonly", "", "required", "", 3, "tabindex"], ["matInput", "", "type", "text", "formControlName", "senderName", "required", "", 3, "tabindex"], ["matInput", "", "type", "text", "formControlName", "address1", "required", "", 3, "tabindex"], ["matInput", "", "type", "text", "formControlName", "address2", "required", "", 3, "tabindex"], ["matInput", "", "type", "text", "formControlName", "address3", "required", "", 3, "tabindex"], [3, "matTooltip", "value"], ["mat-header-cell", ""], ["mat-cell", ""], ["mat-header-cell", "", 1, "text-right"], ["mat-cell", "", 1, "text-right"], ["appNumbersOnly", "", "step", "0.01", "matInput", "", "type", "text", 1, "text-right", "mat-input-batdetails", 3, "ngModelChange", "change", "ngModel", "ngModelOptions"], ["appNumbersOnly", "", "step", "0.01", "matInput", "", "type", "text", 1, "text-right", "mat-input-batdetails", 3, "ngModelChange", "ngModel", "ngModelOptions"], ["mat-header-row", "", "mat-sort-header", "", 1, "table-row1"], ["mat-row", "", 1, "table-row1"], ["type", "file", "name", "stkWthFiles", "multiple", "", 2, "display", "none", 3, "change"]],
      template: function StkwithdrawalEntryComponent_Template(rf, ctx) {
        if (rf & 1) {
          const _r1 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵgetCurrentView"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "form", 10)(1, "div", 11)(2, "div", 12)(3, "div", 13)(4, "div", 14)(5, "mat-form-field", 15)(6, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](7, "Withdrawal Challan");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](8, "input", 16);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](9, "mat-form-field", 15)(10, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](11, "Period");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](12, "input", 17);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](13, "mat-form-field", 15)(14, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](15, "Withdrawal Date");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](16, "input", 18)(17, "mat-datepicker-toggle", 19)(18, "mat-datepicker", null, 0);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](20, "mat-form-field", 15)(21, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](22, "Withdrawal Type");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](23, "mat-select", 20);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵlistener"]("selectionChange", function StkwithdrawalEntryComponent_Template_mat_select_selectionChange_23_listener() {
            _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵrestoreView"](_r1);
            return _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵresetView"](ctx.getSupplierByType());
          });
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](24, StkwithdrawalEntryComponent_mat_option_24_Template, 2, 2, "mat-option", 21);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()()()()();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](25, "div", 12)(26, "div", 13)(27, "div", 22)(28, "span", 23);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](29, "Send To");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](30, "div", 24);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](31, StkwithdrawalEntryComponent_div_31_Template, 1, 0, "div", 25)(32, StkwithdrawalEntryComponent_ng_template_32_Template, 17, 10, "ng-template", null, 1, _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplateRefExtractor"])(34, StkwithdrawalEntryComponent_ng_template_34_Template, 19, 8, "ng-template", null, 2, _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplateRefExtractor"]);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](36, "div", 24)(37, "mat-form-field", 15)(38, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](39, "State");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](40, "mat-select", 26);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](41, StkwithdrawalEntryComponent_mat_option_41_Template, 2, 2, "mat-option", 21);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](42, "mat-form-field", 15)(43, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](44, "Destination");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](45, "input", 27);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](46, "mat-form-field", 15)(47, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](48, "Transporter");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](49, "input", 28);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](50, "mat-form-field", 15)(51, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](52, "No Of Cases");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](53, "input", 29);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](54, "div", 24)(55, "mat-form-field", 15)(56, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](57, "LR Number");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](58, "input", 30);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](59, "mat-form-field", 15)(60, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](61, "LR Date");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](62, "input", 31)(63, "mat-datepicker-toggle", 32)(64, "mat-datepicker", null, 3);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](66, "mat-form-field", 15)(67, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](68, "Display Message");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](69, "input", 33);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](70, "mat-form-field", 15)(71, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](72, "Remarks");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](73, "input", 34);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()()()();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](74, "div", 12)(75, "div", 13)(76, "div", 14)(77, "mat-form-field", 35)(78, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](79, "Item Name");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](80, "mat-select", 36, 4);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵlistener"]("selectionChange", function StkwithdrawalEntryComponent_Template_mat_select_selectionChange_80_listener() {
            _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵrestoreView"](_r1);
            return _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵresetView"](ctx.getBatchDetailsByProdStkType("P"));
          });
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](82, StkwithdrawalEntryComponent_mat_option_82_Template, 2, 4, "mat-option", 37);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](83, "mat-form-field", 35)(84, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](85, "Stock Type");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](86, "mat-select", 38);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵlistener"]("selectionChange", function StkwithdrawalEntryComponent_Template_mat_select_selectionChange_86_listener() {
            _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵrestoreView"](_r1);
            return _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵresetView"](ctx.getBatchDetailsByProdStkType("S"));
          });
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](87, StkwithdrawalEntryComponent_mat_option_87_Template, 2, 2, "mat-option", 21);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()()();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](88, "div", 39)(89, "table", 40);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](90, 41);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](91, StkwithdrawalEntryComponent_th_91_Template, 2, 0, "th", 42)(92, StkwithdrawalEntryComponent_td_92_Template, 2, 1, "td", 43);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](93, 44);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](94, StkwithdrawalEntryComponent_th_94_Template, 2, 0, "th", 45)(95, StkwithdrawalEntryComponent_td_95_Template, 2, 1, "td", 46);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](96, 47);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](97, StkwithdrawalEntryComponent_th_97_Template, 2, 0, "th", 45)(98, StkwithdrawalEntryComponent_td_98_Template, 2, 1, "td", 46);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](99, 48);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](100, StkwithdrawalEntryComponent_th_100_Template, 2, 0, "th", 45)(101, StkwithdrawalEntryComponent_td_101_Template, 2, 1, "td", 46);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](102, 49);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](103, StkwithdrawalEntryComponent_th_103_Template, 2, 0, "th", 45)(104, StkwithdrawalEntryComponent_td_104_Template, 3, 3, "td", 46);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](105, 50);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](106, StkwithdrawalEntryComponent_th_106_Template, 2, 0, "th", 45)(107, StkwithdrawalEntryComponent_td_107_Template, 2, 3, "td", 46);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](108, 51);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](109, StkwithdrawalEntryComponent_th_109_Template, 2, 0, "th", 45)(110, StkwithdrawalEntryComponent_td_110_Template, 2, 1, "td", 46);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](111, 52);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](112, StkwithdrawalEntryComponent_th_112_Template, 2, 0, "th", 45)(113, StkwithdrawalEntryComponent_td_113_Template, 2, 1, "td", 46);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](114, StkwithdrawalEntryComponent_tr_114_Template, 1, 0, "tr", 53)(115, StkwithdrawalEntryComponent_tr_115_Template, 1, 0, "tr", 54);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](116, "mat-paginator", 55);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()()();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](117, "div", 56)(118, "button", 57);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵlistener"]("click", function StkwithdrawalEntryComponent_Template_button_click_118_listener() {
            _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵrestoreView"](_r1);
            return _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵresetView"](ctx.save());
          });
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](119, "Save");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](120, "button", 57);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵlistener"]("click", function StkwithdrawalEntryComponent_Template_button_click_120_listener() {
            _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵrestoreView"](_r1);
            return _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵresetView"](ctx.reset());
          });
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](121, "Reset");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](122, "button", 58);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵlistener"]("click", function StkwithdrawalEntryComponent_Template_button_click_122_listener() {
            _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵrestoreView"](_r1);
            return _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵresetView"](ctx.checkDetails());
          });
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](123, "View");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](124, "button", 59);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵlistener"]("click", function StkwithdrawalEntryComponent_Template_button_click_124_listener() {
            _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵrestoreView"](_r1);
            return _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵresetView"](ctx.createNewWithdrawal());
          });
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](125, "New Withdrawal");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](126, "button", 60);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](127, "Exit");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](128, StkwithdrawalEntryComponent_div_128_Template, 6, 0, "div", 61);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()()();
        }
        if (rf & 2) {
          const stkWithdrawalDate_r21 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵreference"](19);
          const content_r22 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵreference"](33);
          const other_content_r23 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵreference"](35);
          const lrdate_r24 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵreference"](65);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("formGroup", ctx.form);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](2);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵclassMap"](ctx.constants.page_div_class);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](3);
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
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("matDatepicker", stkWithdrawalDate_r21)("max", ctx.maxDate)("min", ctx.minDate);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("for", stkWithdrawalDate_r21)("disabled", ctx.headerFreezed);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngClass", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngClass", "mr-r-10")("disabled", ctx.headerFreezed);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngForOf", ctx.stkWithTypeList);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵclassMap"](ctx.constants.page_div_class);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](6);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngIf", ctx.showSupplierList)("ngIfThen", content_r22)("ngIfElse", other_content_r23);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](6);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngClass", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngClass", "mr-r-10")("disabled", ctx.headerFreezed);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngForOf", ctx.stateList);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngClass", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("tabindex", 6);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngClass", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("tabindex", 7);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngClass", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("tabindex", 10);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](2);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngClass", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("tabindex", 8);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngClass", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("matDatepicker", lrdate_r24)("max", ctx.maxDate);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("for", lrdate_r24)("disabled", ctx.headerFreezed)("tabindex", 9);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngClass", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("tabindex", 12);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngClass", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("tabindex", 13);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵclassMap"](ctx.constants.page_div_class);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngClass", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngClass", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](2);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngForOf", ctx.prodList);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngClass", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngClass", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngForOf", ctx.stockTypes);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](2);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("dataSource", ctx.batchList);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](25);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("matHeaderRowDef", ctx.displayedColumns)("matHeaderRowDefSticky", true);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("matRowDefColumns", ctx.displayedColumns);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("pageSizeOptions", ctx.constants.paginator_upto_50);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](2);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵclassMapInterpolate1"]("mr-r-10  ", ctx.constants.save_btn_class, "");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](2);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵclassMapInterpolate1"]("mr-r-10  ", ctx.constants.save_btn_class, " ");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](6);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵclassMapInterpolate1"]("mr-r-10  ", ctx.constants.exit_btn_class, "");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](2);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngIf", ctx.showUpload);
        }
      },
      dependencies: [_angular_router__WEBPACK_IMPORTED_MODULE_11__.RouterLink, _angular_common__WEBPACK_IMPORTED_MODULE_9__.NgClass, _angular_common__WEBPACK_IMPORTED_MODULE_9__.NgForOf, _angular_common__WEBPACK_IMPORTED_MODULE_9__.NgIf, _angular_forms__WEBPACK_IMPORTED_MODULE_6__["ɵNgNoValidate"], _angular_forms__WEBPACK_IMPORTED_MODULE_6__.DefaultValueAccessor, _angular_forms__WEBPACK_IMPORTED_MODULE_6__.NgControlStatus, _angular_forms__WEBPACK_IMPORTED_MODULE_6__.NgControlStatusGroup, _angular_forms__WEBPACK_IMPORTED_MODULE_6__.RequiredValidator, _angular_forms__WEBPACK_IMPORTED_MODULE_6__.NgModel, _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormGroupDirective, _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControlName, _angular_material_form_field__WEBPACK_IMPORTED_MODULE_12__.MatFormField, _angular_material_form_field__WEBPACK_IMPORTED_MODULE_12__.MatLabel, _angular_material_form_field__WEBPACK_IMPORTED_MODULE_12__.MatSuffix, _angular_material_button__WEBPACK_IMPORTED_MODULE_13__.MatButton, _angular_flex_layout_flex__WEBPACK_IMPORTED_MODULE_14__.DefaultLayoutDirective, _angular_flex_layout_flex__WEBPACK_IMPORTED_MODULE_14__.DefaultFlexDirective, _angular_flex_layout_extended__WEBPACK_IMPORTED_MODULE_15__.DefaultClassDirective, _angular_material_input__WEBPACK_IMPORTED_MODULE_16__.MatInput, _angular_material_select__WEBPACK_IMPORTED_MODULE_17__.MatSelect, _angular_material_core__WEBPACK_IMPORTED_MODULE_18__.MatOption, _angular_material_datepicker__WEBPACK_IMPORTED_MODULE_19__.MatDatepicker, _angular_material_datepicker__WEBPACK_IMPORTED_MODULE_19__.MatDatepickerInput, _angular_material_datepicker__WEBPACK_IMPORTED_MODULE_19__.MatDatepickerToggle, _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatTable, _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatHeaderCellDef, _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatHeaderRowDef, _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatColumnDef, _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatCellDef, _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatRowDef, _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatHeaderCell, _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatCell, _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatHeaderRow, _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatRow, _angular_material_paginator__WEBPACK_IMPORTED_MODULE_10__.MatPaginator, _angular_material_tooltip__WEBPACK_IMPORTED_MODULE_20__.MatTooltip, _directives_utility_numbers_only_directive__WEBPACK_IMPORTED_MODULE_4__.NumbersOnlyDirective],
      styles: ["/*# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IiIsInNvdXJjZVJvb3QiOiIifQ== */"]
    });
  }
  return StkwithdrawalEntryComponent;
})();
let StockWthDetailComponent = /*#__PURE__*/(() => {
  class StockWthDetailComponent {
    dialogRef;
    data;
    constructor(dialogRef, data) {
      this.dialogRef = dialogRef;
      this.data = data;
    }
    onNoClick() {
      this.dialogRef.close();
    }
    static ɵfac = function StockWthDetailComponent_Factory(__ngFactoryType__) {
      return new (__ngFactoryType__ || StockWthDetailComponent)(_angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵdirectiveInject"](_angular_material_dialog__WEBPACK_IMPORTED_MODULE_8__.MatDialogRef), _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵdirectiveInject"](_angular_material_dialog__WEBPACK_IMPORTED_MODULE_8__.MAT_DIALOG_DATA));
    };
    static ɵcmp = /*@__PURE__*/_angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵdefineComponent"]({
      type: StockWthDetailComponent,
      selectors: [["stk-wth-detail-dialog"]],
      decls: 23,
      vars: 1,
      consts: [["mat-dialog-title", ""], ["mat-dialog-content", ""], [1, "mytable", "table-full-width"], [4, "ngFor", "ngForOf"], ["mat-dialog-actions", "", 1, "mt-1"], ["mat-raised-button", "", "color", "primary", "mat-dialog-close", ""], ["align", "right"]],
      template: function StockWthDetailComponent_Template(rf, ctx) {
        if (rf & 1) {
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "h1", 0);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1, "Product Details");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](2, "div", 1)(3, "table", 2)(4, "thead")(5, "tr")(6, "th");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](7, "Item Name");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](8, "th");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](9, "Stock Type");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](10, "th");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](11, "Batch No");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](12, "th");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](13, "Qty");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](14, "th");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](15, "Cases");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](16, "th");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](17, "Rate");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()()();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](18, "tbody");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](19, StockWthDetailComponent_tr_19_Template, 13, 6, "tr", 3);
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
  return StockWthDetailComponent;
})();

/***/ }),

/***/ 85996:
/*!***********************************************************************************************!*\
  !*** ./src/app/components/transaction/stkwithdrawal-modify/stkwithdrawal-modify.component.ts ***!
  \***********************************************************************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   StkwithdrawalModifyComponent: () => (/* binding */ StkwithdrawalModifyComponent)
/* harmony export */ });
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! @angular/forms */ 34456);
/* harmony import */ var _classes_constants__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ../../../classes/constants */ 5556);
/* harmony import */ var _angular_material_table__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! @angular/material/table */ 77697);
/* harmony import */ var _angular_material_paginator__WEBPACK_IMPORTED_MODULE_9__ = __webpack_require__(/*! @angular/material/paginator */ 24624);
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! @angular/core */ 37580);
/* harmony import */ var _services_stk_withdrawal_stk_withdrawal_service__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ../../../services/stk-withdrawal/stk-withdrawal.service */ 74303);
/* harmony import */ var _classes_medico_utility__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ../../../classes/medico-utility */ 79955);
/* harmony import */ var _services_medico_medico_service__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ../../../services/medico/medico.service */ 353);
/* harmony import */ var _angular_material_dialog__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! @angular/material/dialog */ 12587);
/* harmony import */ var _angular_common__WEBPACK_IMPORTED_MODULE_8__ = __webpack_require__(/*! @angular/common */ 60316);
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_10__ = __webpack_require__(/*! @angular/router */ 95072);
/* harmony import */ var _angular_material_form_field__WEBPACK_IMPORTED_MODULE_11__ = __webpack_require__(/*! @angular/material/form-field */ 24950);
/* harmony import */ var _angular_material_button__WEBPACK_IMPORTED_MODULE_12__ = __webpack_require__(/*! @angular/material/button */ 84175);
/* harmony import */ var _angular_flex_layout_flex__WEBPACK_IMPORTED_MODULE_13__ = __webpack_require__(/*! @angular/flex-layout/flex */ 91447);
/* harmony import */ var _angular_flex_layout_extended__WEBPACK_IMPORTED_MODULE_14__ = __webpack_require__(/*! @angular/flex-layout/extended */ 52913);
/* harmony import */ var _angular_material_input__WEBPACK_IMPORTED_MODULE_15__ = __webpack_require__(/*! @angular/material/input */ 95541);
/* harmony import */ var _angular_material_datepicker__WEBPACK_IMPORTED_MODULE_16__ = __webpack_require__(/*! @angular/material/datepicker */ 61977);




















const _c0 = a0 => ["/stkwithdrawal/modifyContent", a0];
function StkwithdrawalModifyComponent_th_26_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](0, "th", 28);
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtext"](1, "Challan Number");
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]();
  }
}
function StkwithdrawalModifyComponent_td_27_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](0, "td", 29)(1, "a", 30);
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtext"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    const row_r2 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵproperty"]("routerLink", _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵpureFunction1"](2, _c0, row_r2.swv_id));
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtextInterpolate1"]("", row_r2.swv_challan_no, " ");
  }
}
function StkwithdrawalModifyComponent_th_29_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](0, "th", 31);
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtext"](1, "Challan Date");
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]();
  }
}
function StkwithdrawalModifyComponent_td_30_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](0, "td", 32);
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const row_r3 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtextInterpolate"](row_r3.swv_challan_dt);
  }
}
function StkwithdrawalModifyComponent_th_32_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](0, "th", 28);
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtext"](1, "Withdrawal Type");
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]();
  }
}
function StkwithdrawalModifyComponent_td_33_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](0, "td", 29);
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const row_r4 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtextInterpolate"](row_r4.swv_type_name);
  }
}
function StkwithdrawalModifyComponent_th_35_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](0, "th", 28);
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtext"](1, "Sender Name");
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]();
  }
}
function StkwithdrawalModifyComponent_td_36_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](0, "td", 29);
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const row_r5 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtextInterpolate"](row_r5.swv_sender_name);
  }
}
function StkwithdrawalModifyComponent_th_38_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](0, "th", 28);
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtext"](1, "State");
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]();
  }
}
function StkwithdrawalModifyComponent_td_39_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](0, "td", 29);
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const row_r6 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtextInterpolate"](row_r6.state_name);
  }
}
function StkwithdrawalModifyComponent_th_41_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](0, "th", 28);
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtext"](1, "Remove");
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]();
  }
}
function StkwithdrawalModifyComponent_td_42_Template(rf, ctx) {
  if (rf & 1) {
    const _r7 = _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵgetCurrentView"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](0, "td", 29)(1, "button", 33);
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵlistener"]("click", function StkwithdrawalModifyComponent_td_42_Template_button_click_1_listener() {
      const ctx_r7 = _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵrestoreView"](_r7);
      const row_r9 = ctx_r7.$implicit;
      const i_r10 = ctx_r7.index;
      const ctx_r10 = _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵnextContext"]();
      return _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵresetView"](ctx_r10.removeStkWithdrawal(row_r9.swv_id, i_r10));
    });
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtext"](2, "Remove");
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]()();
  }
}
function StkwithdrawalModifyComponent_tr_43_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelement"](0, "tr", 34);
  }
}
function StkwithdrawalModifyComponent_tr_44_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelement"](0, "tr", 35);
  }
}
let StkwithdrawalModifyComponent = /*#__PURE__*/(() => {
  class StkwithdrawalModifyComponent {
    stkWithdrawalService;
    medicoUtility;
    medicoService;
    fb;
    dialog;
    el;
    datepipe;
    session;
    constants = new _classes_constants__WEBPACK_IMPORTED_MODULE_0__.Constants();
    maxDate;
    stkWthList;
    table;
    paginator;
    displayedColumns = ["ChallanNumber", "ChallanDate", "WithdrawalType", "SenderName", "State", "Remove"];
    form;
    constructor(stkWithdrawalService, medicoUtility, medicoService, fb, dialog, el, datepipe) {
      this.stkWithdrawalService = stkWithdrawalService;
      this.medicoUtility = medicoUtility;
      this.medicoService = medicoService;
      this.fb = fb;
      this.dialog = dialog;
      this.el = el;
      this.datepipe = datepipe;
      this.form = this.fb.group({
        fromDate: new _angular_forms__WEBPACK_IMPORTED_MODULE_5__.FormControl(new Date(), _angular_forms__WEBPACK_IMPORTED_MODULE_5__.Validators.required),
        toDate: new _angular_forms__WEBPACK_IMPORTED_MODULE_5__.FormControl(new Date(), _angular_forms__WEBPACK_IMPORTED_MODULE_5__.Validators.required)
      });
      this.medicoService.headingName = "Stock Withdrawal Modify";
      this.medicoUtility.toggleProgressBar(true);
      this.session = this.medicoUtility.getSessionVariables();
      this.maxDate = new Date();
      this.medicoUtility.toggleProgressBar(false);
    }
    ngOnInit() {}
    searchStockWithdrawls() {
      if (this.form.get("fromDate")?.valid && this.form.get("toDate")?.valid) {
        this.medicoUtility.toggleProgressBar(true);
        let fDate = this.datepipe.transform(this.form.get("fromDate")?.value, 'dd/MM/yyyy');
        let tDate = this.datepipe.transform(this.form.get("toDate")?.value, 'dd/MM/yyyy');
        this.stkWithdrawalService.getSwvList(this.session.EMP_ID, this.session.USER_TYPE, fDate, tDate).subscribe(response => {
          this.stkWthList = new _angular_material_table__WEBPACK_IMPORTED_MODULE_6__.MatTableDataSource(response.stkWthList.slice());
          this.stkWthList.paginator = this.paginator;
          this.medicoUtility.toggleProgressBar(false);
        });
      }
    }
    removeStkWithdrawal(headerId, index) {
      this.medicoService.confirmBox("Confirmation", "Are you sure to delete this Stock Withdrawal?", "Ok", "Cancel").subscribe(result => {
        if (result == true) {
          this.deleteStockWithdrawal(headerId, index);
        }
      });
    }
    deleteStockWithdrawal(headerId, index) {
      this.medicoUtility.toggleProgressBar(true);
      try {
        this.stkWithdrawalService.deleteStkWithdrawal(this.session.EMP_ID, headerId).subscribe(response => {
          const data = this.stkWthList.data;
          data.splice(this.paginator.pageIndex * this.paginator.pageSize + index, 1);
          this.stkWthList.data = data;
          this.medicoService.popup("Message", "Stock Withdrawal deleted successfully");
          this.medicoUtility.toggleProgressBar(false);
        }, error => {
          this.medicoUtility.toggleProgressBar(false);
          this.medicoService.popup("Message", "Network Error Occurred!");
        });
      } catch (error) {
        this.medicoUtility.toggleProgressBar(false);
        this.medicoService.popup("Message", "Network Error Occurred!");
      }
    }
    static ɵfac = function StkwithdrawalModifyComponent_Factory(__ngFactoryType__) {
      return new (__ngFactoryType__ || StkwithdrawalModifyComponent)(_angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵdirectiveInject"](_services_stk_withdrawal_stk_withdrawal_service__WEBPACK_IMPORTED_MODULE_1__.StkWithdrawalService), _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵdirectiveInject"](_classes_medico_utility__WEBPACK_IMPORTED_MODULE_2__.MedicoUtility), _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵdirectiveInject"](_services_medico_medico_service__WEBPACK_IMPORTED_MODULE_3__.MedicoService), _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵdirectiveInject"](_angular_forms__WEBPACK_IMPORTED_MODULE_5__.FormBuilder), _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵdirectiveInject"](_angular_material_dialog__WEBPACK_IMPORTED_MODULE_7__.MatDialog), _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵdirectiveInject"](_angular_core__WEBPACK_IMPORTED_MODULE_4__.ElementRef), _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵdirectiveInject"](_angular_common__WEBPACK_IMPORTED_MODULE_8__.DatePipe));
    };
    static ɵcmp = /*@__PURE__*/_angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵdefineComponent"]({
      type: StkwithdrawalModifyComponent,
      selectors: [["app-stkwithdrawal-modify"]],
      viewQuery: function StkwithdrawalModifyComponent_Query(rf, ctx) {
        if (rf & 1) {
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵviewQuery"](_angular_material_table__WEBPACK_IMPORTED_MODULE_6__.MatTable, 5);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵviewQuery"](_angular_material_paginator__WEBPACK_IMPORTED_MODULE_9__.MatPaginator, 5);
        }
        if (rf & 2) {
          let _t;
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵqueryRefresh"](_t = _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵloadQuery"]()) && (ctx.table = _t.first);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵqueryRefresh"](_t = _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵloadQuery"]()) && (ctx.paginator = _t.first);
        }
      },
      decls: 46,
      vars: 20,
      consts: [["fromDate", ""], ["toDate", ""], [3, "formGroup"], [1, "pl-1", "pr-1", "pb-1", "container"], ["fxLayout", "row", "fxLayout.lt-md", "column"], ["fxFlex", "100%"], ["fxLayout", "row", "fxLayout.lt-md", "column", 1, "pl-1", "pt-1"], ["fxFlex", "25%", "appearance", "outline", 3, "ngClass"], ["matInput", "", "placeholder", "From Date", "required", "", "formControlName", "fromDate", "readonly", "", 1, "mat-datepicker-input", 3, "matDatepicker", "max"], ["matSuffix", "", 3, "for"], ["matInput", "", "placeholder", "To Date", "required", "", "formControlName", "toDate", "readonly", "", 1, "mat-datepicker-input", 3, "matDatepicker", "max"], ["mat-raised-button", "", "type", "button", 3, "click"], ["fxFlex", "100%", 1, "mt-mr-10"], [1, "table-container", "mat-elevation-z2", "width-only-100"], ["mat-table", "", "matSort", "", 1, "table-borders-right", "width-only-100", "table-th-bg-primary", "thin-th", "mat-td-p", "mat-th-p", 3, "dataSource"], ["matColumnDef", "ChallanNumber"], ["mat-header-cell", "", 4, "matHeaderCellDef"], ["mat-cell", "", 4, "matCellDef"], ["matColumnDef", "ChallanDate"], ["mat-header-cell", "", "class", "text-right", 4, "matHeaderCellDef"], ["mat-cell", "", "class", "text-right", 4, "matCellDef"], ["matColumnDef", "WithdrawalType"], ["matColumnDef", "SenderName"], ["matColumnDef", "State"], ["matColumnDef", "Remove"], ["mat-header-row", "", "mat-sort-header", "", "class", "table-row1", 4, "matHeaderRowDef", "matHeaderRowDefSticky"], ["mat-row", "", "class", "table-row1", 4, "matRowDef", "matRowDefColumns"], ["showFirstLastButtons", "", 3, "pageSizeOptions"], ["mat-header-cell", ""], ["mat-cell", ""], [1, "nav-link", "link", 3, "routerLink"], ["mat-header-cell", "", 1, "text-right"], ["mat-cell", "", 1, "text-right"], ["mat-raised-button", "", "color", "warn", 3, "click"], ["mat-header-row", "", "mat-sort-header", "", 1, "table-row1"], ["mat-row", "", 1, "table-row1"]],
      template: function StkwithdrawalModifyComponent_Template(rf, ctx) {
        if (rf & 1) {
          const _r1 = _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵgetCurrentView"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](0, "form", 2)(1, "div", 3)(2, "div", 4)(3, "div", 5)(4, "div", 6)(5, "mat-form-field", 7)(6, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtext"](7, "From Date");
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelement"](8, "input", 8)(9, "mat-datepicker-toggle", 9)(10, "mat-datepicker", null, 0);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](12, "mat-form-field", 7)(13, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtext"](14, "To Date");
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelement"](15, "input", 10)(16, "mat-datepicker-toggle", 9)(17, "mat-datepicker", null, 1);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](19, "button", 11);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵlistener"]("click", function StkwithdrawalModifyComponent_Template_button_click_19_listener() {
            _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵrestoreView"](_r1);
            return _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵresetView"](ctx.searchStockWithdrawls());
          });
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtext"](20, "Search");
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]()()()();
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementStart"](21, "div", 4)(22, "div", 12)(23, "div", 13)(24, "table", 14);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementContainerStart"](25, 15);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtemplate"](26, StkwithdrawalModifyComponent_th_26_Template, 2, 0, "th", 16)(27, StkwithdrawalModifyComponent_td_27_Template, 3, 4, "td", 17);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementContainerStart"](28, 18);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtemplate"](29, StkwithdrawalModifyComponent_th_29_Template, 2, 0, "th", 19)(30, StkwithdrawalModifyComponent_td_30_Template, 2, 1, "td", 20);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementContainerStart"](31, 21);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtemplate"](32, StkwithdrawalModifyComponent_th_32_Template, 2, 0, "th", 16)(33, StkwithdrawalModifyComponent_td_33_Template, 2, 1, "td", 17);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementContainerStart"](34, 22);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtemplate"](35, StkwithdrawalModifyComponent_th_35_Template, 2, 0, "th", 16)(36, StkwithdrawalModifyComponent_td_36_Template, 2, 1, "td", 17);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementContainerStart"](37, 23);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtemplate"](38, StkwithdrawalModifyComponent_th_38_Template, 2, 0, "th", 16)(39, StkwithdrawalModifyComponent_td_39_Template, 2, 1, "td", 17);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementContainerStart"](40, 24);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtemplate"](41, StkwithdrawalModifyComponent_th_41_Template, 2, 0, "th", 16)(42, StkwithdrawalModifyComponent_td_42_Template, 3, 0, "td", 17);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵtemplate"](43, StkwithdrawalModifyComponent_tr_43_Template, 1, 0, "tr", 25)(44, StkwithdrawalModifyComponent_tr_44_Template, 1, 0, "tr", 26);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelement"](45, "mat-paginator", 27);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵelementEnd"]()()()()();
        }
        if (rf & 2) {
          const fromDate_r12 = _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵreference"](11);
          const toDate_r13 = _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵreference"](18);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵproperty"]("formGroup", ctx.form);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵadvance"](2);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵclassMap"](ctx.constants.page_div_class);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵproperty"]("ngClass", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵproperty"]("matDatepicker", fromDate_r12)("max", ctx.maxDate);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵproperty"]("for", fromDate_r12);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵproperty"]("ngClass", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵproperty"]("matDatepicker", toDate_r13)("max", ctx.maxDate);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵproperty"]("for", toDate_r13);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵclassMapInterpolate1"]("mr-r-10 ", ctx.constants.save_btn_class, "");
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵadvance"](5);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵproperty"]("dataSource", ctx.stkWthList);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵadvance"](19);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵproperty"]("matHeaderRowDef", ctx.displayedColumns)("matHeaderRowDefSticky", true);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵproperty"]("matRowDefColumns", ctx.displayedColumns);
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_4__["ɵɵproperty"]("pageSizeOptions", ctx.constants.paginator_upto_1000);
        }
      },
      dependencies: [_angular_router__WEBPACK_IMPORTED_MODULE_10__.RouterLink, _angular_common__WEBPACK_IMPORTED_MODULE_8__.NgClass, _angular_forms__WEBPACK_IMPORTED_MODULE_5__["ɵNgNoValidate"], _angular_forms__WEBPACK_IMPORTED_MODULE_5__.DefaultValueAccessor, _angular_forms__WEBPACK_IMPORTED_MODULE_5__.NgControlStatus, _angular_forms__WEBPACK_IMPORTED_MODULE_5__.NgControlStatusGroup, _angular_forms__WEBPACK_IMPORTED_MODULE_5__.RequiredValidator, _angular_forms__WEBPACK_IMPORTED_MODULE_5__.FormGroupDirective, _angular_forms__WEBPACK_IMPORTED_MODULE_5__.FormControlName, _angular_material_form_field__WEBPACK_IMPORTED_MODULE_11__.MatFormField, _angular_material_form_field__WEBPACK_IMPORTED_MODULE_11__.MatLabel, _angular_material_form_field__WEBPACK_IMPORTED_MODULE_11__.MatSuffix, _angular_material_button__WEBPACK_IMPORTED_MODULE_12__.MatButton, _angular_flex_layout_flex__WEBPACK_IMPORTED_MODULE_13__.DefaultLayoutDirective, _angular_flex_layout_flex__WEBPACK_IMPORTED_MODULE_13__.DefaultFlexDirective, _angular_flex_layout_extended__WEBPACK_IMPORTED_MODULE_14__.DefaultClassDirective, _angular_material_input__WEBPACK_IMPORTED_MODULE_15__.MatInput, _angular_material_datepicker__WEBPACK_IMPORTED_MODULE_16__.MatDatepicker, _angular_material_datepicker__WEBPACK_IMPORTED_MODULE_16__.MatDatepickerInput, _angular_material_datepicker__WEBPACK_IMPORTED_MODULE_16__.MatDatepickerToggle, _angular_material_table__WEBPACK_IMPORTED_MODULE_6__.MatTable, _angular_material_table__WEBPACK_IMPORTED_MODULE_6__.MatHeaderCellDef, _angular_material_table__WEBPACK_IMPORTED_MODULE_6__.MatHeaderRowDef, _angular_material_table__WEBPACK_IMPORTED_MODULE_6__.MatColumnDef, _angular_material_table__WEBPACK_IMPORTED_MODULE_6__.MatCellDef, _angular_material_table__WEBPACK_IMPORTED_MODULE_6__.MatRowDef, _angular_material_table__WEBPACK_IMPORTED_MODULE_6__.MatHeaderCell, _angular_material_table__WEBPACK_IMPORTED_MODULE_6__.MatCell, _angular_material_table__WEBPACK_IMPORTED_MODULE_6__.MatHeaderRow, _angular_material_table__WEBPACK_IMPORTED_MODULE_6__.MatRow, _angular_material_paginator__WEBPACK_IMPORTED_MODULE_9__.MatPaginator],
      styles: ["/*# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IiIsInNvdXJjZVJvb3QiOiIifQ== */"]
    });
  }
  return StkwithdrawalModifyComponent;
})();

/***/ }),

/***/ 21658:
/*!*************************************************************************************************************!*\
  !*** ./src/app/components/transaction/stkwithdrawal-modifycontent/stkwithdrawal-modifycontent.component.ts ***!
  \*************************************************************************************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   StkwithdrawalModifycontentComponent: () => (/* binding */ StkwithdrawalModifycontentComponent),
/* harmony export */   StockWthModifyDetailComponent: () => (/* binding */ StockWthModifyDetailComponent)
/* harmony export */ });
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! @angular/forms */ 34456);
/* harmony import */ var _classes_constants__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ../../../classes/constants */ 5556);
/* harmony import */ var _angular_material_table__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! @angular/material/table */ 77697);
/* harmony import */ var _angular_material_paginator__WEBPACK_IMPORTED_MODULE_11__ = __webpack_require__(/*! @angular/material/paginator */ 24624);
/* harmony import */ var _angular_material_dialog__WEBPACK_IMPORTED_MODULE_8__ = __webpack_require__(/*! @angular/material/dialog */ 12587);
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! @angular/core */ 37580);
/* harmony import */ var _services_stk_withdrawal_stk_withdrawal_service__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ../../../services/stk-withdrawal/stk-withdrawal.service */ 74303);
/* harmony import */ var _classes_medico_utility__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ../../../classes/medico-utility */ 79955);
/* harmony import */ var _services_medico_medico_service__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ../../../services/medico/medico.service */ 353);
/* harmony import */ var _angular_common__WEBPACK_IMPORTED_MODULE_9__ = __webpack_require__(/*! @angular/common */ 60316);
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_10__ = __webpack_require__(/*! @angular/router */ 95072);
/* harmony import */ var _angular_material_form_field__WEBPACK_IMPORTED_MODULE_12__ = __webpack_require__(/*! @angular/material/form-field */ 24950);
/* harmony import */ var _angular_material_button__WEBPACK_IMPORTED_MODULE_13__ = __webpack_require__(/*! @angular/material/button */ 84175);
/* harmony import */ var _angular_flex_layout_flex__WEBPACK_IMPORTED_MODULE_14__ = __webpack_require__(/*! @angular/flex-layout/flex */ 91447);
/* harmony import */ var _angular_flex_layout_extended__WEBPACK_IMPORTED_MODULE_15__ = __webpack_require__(/*! @angular/flex-layout/extended */ 52913);
/* harmony import */ var _angular_material_input__WEBPACK_IMPORTED_MODULE_16__ = __webpack_require__(/*! @angular/material/input */ 95541);
/* harmony import */ var _angular_material_select__WEBPACK_IMPORTED_MODULE_17__ = __webpack_require__(/*! @angular/material/select */ 25175);
/* harmony import */ var _angular_material_core__WEBPACK_IMPORTED_MODULE_18__ = __webpack_require__(/*! @angular/material/core */ 74646);
/* harmony import */ var _angular_material_tooltip__WEBPACK_IMPORTED_MODULE_19__ = __webpack_require__(/*! @angular/material/tooltip */ 80640);
/* harmony import */ var _directives_utility_numbers_only_directive__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ../../../directives/utility/numbers-only.directive */ 22128);
























const _c0 = ["qtyToReduce"];
const _c1 = ["product"];
const _c2 = () => ({
  standalone: true
});
function StkwithdrawalModifycontentComponent_mat_option_101_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "mat-option", 57);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const p_r2 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵpropertyInterpolate"]("matTooltip", p_r2.smp_prod_name);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("value", p_r2.smp_prod_id);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵattribute"]("data-divId", p_r2.smp_std_div_id);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](p_r2.smp_prod_name);
  }
}
function StkwithdrawalModifycontentComponent_mat_option_106_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "mat-option", 58);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const s_r3 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("value", s_r3.sgprmdet_nm);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](s_r3.sgprmdet_disp_nm);
  }
}
function StkwithdrawalModifycontentComponent_th_110_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "th", 59);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1, "Batch No");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
}
function StkwithdrawalModifycontentComponent_td_111_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "td", 60);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const row_r4 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](row_r4.batch_no);
  }
}
function StkwithdrawalModifycontentComponent_th_113_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "th", 59);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1, "Stock");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
}
function StkwithdrawalModifycontentComponent_td_114_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "td", 61);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const row_r5 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](row_r5.stock.toFixed(2));
  }
}
function StkwithdrawalModifycontentComponent_th_116_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "th", 59);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1, "Mfg Date");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
}
function StkwithdrawalModifycontentComponent_td_117_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "td", 60);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const row_r6 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](row_r6.mfg_dt);
  }
}
function StkwithdrawalModifycontentComponent_th_119_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "th", 59);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1, "Expiry");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
}
function StkwithdrawalModifycontentComponent_td_120_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "td", 60);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const row_r7 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](row_r7.exp_dt);
  }
}
function StkwithdrawalModifycontentComponent_th_122_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "th", 59);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1, "Qty Withdrawn");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
}
function StkwithdrawalModifycontentComponent_td_123_Template(rf, ctx) {
  if (rf & 1) {
    const _r8 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵgetCurrentView"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "td", 61)(1, "input", 62, 4);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtwoWayListener"]("ngModelChange", function StkwithdrawalModifycontentComponent_td_123_Template_input_ngModelChange_1_listener($event) {
      const i_r9 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵrestoreView"](_r8).index;
      const ctx_r9 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"]();
      _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtwoWayBindingSet"](ctx_r9.batchWthQtys[i_r9], $event) || (ctx_r9.batchWthQtys[i_r9] = $event);
      return _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵresetView"]($event);
    });
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵlistener"]("change", function StkwithdrawalModifycontentComponent_td_123_Template_input_change_1_listener($event) {
      const i_r9 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵrestoreView"](_r8).index;
      const ctx_r9 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"]();
      return _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵresetView"](ctx_r9.setRemainingStock($event, i_r9));
    });
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    const i_r9 = ctx.index;
    const ctx_r9 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtwoWayProperty"]("ngModel", ctx_r9.batchWthQtys[i_r9]);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngModelOptions", _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵpureFunction0"](2, _c2));
  }
}
function StkwithdrawalModifycontentComponent_th_125_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "th", 59);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1, "Cases");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
}
function StkwithdrawalModifycontentComponent_td_126_Template(rf, ctx) {
  if (rf & 1) {
    const _r11 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵgetCurrentView"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "td", 61)(1, "input", 63);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtwoWayListener"]("ngModelChange", function StkwithdrawalModifycontentComponent_td_126_Template_input_ngModelChange_1_listener($event) {
      const i_r12 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵrestoreView"](_r11).index;
      const ctx_r9 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"]();
      _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtwoWayBindingSet"](ctx_r9.batchCases[i_r12], $event) || (ctx_r9.batchCases[i_r12] = $event);
      return _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵresetView"]($event);
    });
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    const i_r12 = ctx.index;
    const ctx_r9 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtwoWayProperty"]("ngModel", ctx_r9.batchCases[i_r12]);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngModelOptions", _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵpureFunction0"](2, _c2));
  }
}
function StkwithdrawalModifycontentComponent_th_128_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "th", 59);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1, "Rate");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
}
function StkwithdrawalModifycontentComponent_td_129_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "td", 61);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const row_r13 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](row_r13.rate.toFixed(2));
  }
}
function StkwithdrawalModifycontentComponent_th_131_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "th", 59);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1, "Stock After Withdrawal");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
}
function StkwithdrawalModifycontentComponent_td_132_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "td", 61);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const i_r14 = ctx.index;
    const ctx_r9 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate1"](" ", ctx_r9.stkQtyAfterWth[i_r14], " ");
  }
}
function StkwithdrawalModifycontentComponent_tr_133_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](0, "tr", 64);
  }
}
function StkwithdrawalModifycontentComponent_tr_134_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](0, "tr", 65);
  }
}
function StockWthModifyDetailComponent_tr_19_Template(rf, ctx) {
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
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](p_r1.smp_prod_name);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](p_r1.stock_type_name);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](p_r1.batch_no);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](p_r1.swvdtl_disp_qty.toFixed(2));
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](p_r1.swvdtl_cases);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](p_r1.swvdtl_rate.toFixed(2));
  }
}
let StkwithdrawalModifycontentComponent = /*#__PURE__*/(() => {
  class StkwithdrawalModifycontentComponent {
    stkWithdrawalService;
    medicoUtility;
    medicoService;
    fb;
    dialog;
    el;
    datepipe;
    route;
    session;
    constants = new _classes_constants__WEBPACK_IMPORTED_MODULE_0__.Constants();
    headerId;
    mode;
    stockTypes = null;
    prodList = null;
    batchList;
    batchStocks = new Array();
    batchIds = new Array();
    detailIds = new Array();
    rates = new Array();
    batchWthQtys = new Array();
    batchCases = new Array();
    stkQtyAfterWth = new Array();
    stock_type;
    qtyToReduce;
    product;
    paginator;
    displayedColumns = ["BatchNo", "Stock", "MfgDate", "Expiry", "qty", "cases", "rate", "stkAfterWth"];
    file1;
    file2;
    form;
    constructor(stkWithdrawalService, medicoUtility, medicoService, fb, dialog, el, datepipe, route) {
      this.stkWithdrawalService = stkWithdrawalService;
      this.medicoUtility = medicoUtility;
      this.medicoService = medicoService;
      this.fb = fb;
      this.dialog = dialog;
      this.el = el;
      this.datepipe = datepipe;
      this.route = route;
      this.form = this.fb.group({
        headerId: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(0),
        action: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl("S"),
        detailId: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(0),
        divId: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(0),
        currPeriodCode: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        currFinYear: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        empId: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        sendSubCompId: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        sendLocId: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        receipt_datetime: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        stkWithdrawalNo: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        period: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        stkWithdrawalDate: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        stkWithdrawalType: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        senderId: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        senderName: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        address1: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        address2: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        address3: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        destination: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        transporter: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        lrnumber: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        lrdate: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        noOfCases: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(0),
        state: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        stateId: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        displayMsg: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        remarks: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        productId: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl("", _angular_forms__WEBPACK_IMPORTED_MODULE_6__.Validators.required),
        stockType: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl("", _angular_forms__WEBPACK_IMPORTED_MODULE_6__.Validators.required),
        batchIds: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        detailIds: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        batchStocks: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        rates: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        batchWthQtys: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        batchCases: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        stkQtyAfterWth: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        nstkWithdrawalDate: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(),
        nlrdate: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl("")
      });
      this.medicoService.headingName = "Stock Withdrawal Modify";
      this.medicoUtility.toggleProgressBar(true);
      this.session = this.medicoUtility.getSessionVariables();
      this.medicoUtility.toggleProgressBar(false);
      this.route.params.subscribe(params => {
        this.headerId = params['headerId'];
      });
      this.form.get("headerId")?.setValue(this.headerId);
      this.getDataForStkWithdrawalModify();
    }
    ngOnInit() {}
    viewFiles() {
      if (this.file1 !== '' || this.file1 !== null) {
        this.medicoUtility.openLink("/show-stkwth/" + this.file1);
        //window.open(this.constants.SERVER_SERVLET_CONTEXT_NAME +"/show-stkwth/"+this.file1,"_self");
      }
      if (this.file2 !== '' || this.file2 !== null) {
        this.medicoUtility.openLink("/show-stkwth/" + this.file2);
        // window.open(this.constants.SERVER_SERVLET_CONTEXT_NAME +"/show-stkwth/"+this.file2,"_self");
      }
    }
    getDataForStkWithdrawalModify() {
      this.medicoUtility.toggleProgressBar(true);
      this.stkWithdrawalService.getDataForStkWthModify(this.session.EMP_ID, this.headerId).subscribe(response => {
        this.form.get("sendSubCompId")?.setValue(response.sendSubCompId);
        this.form.get("sendLocId")?.setValue(response.sendLocId);
        this.form.get("stkWithdrawalNo")?.setValue(response.swv_Header.swv_challan_no);
        this.form.get("period")?.setValue(response.period_alt_name);
        this.form.get("stkWithdrawalDate")?.setValue(response.swv_Header.swv_challan_dt);
        this.form.get("stkWithdrawalType")?.setValue(response.swv_Header.swv_type_name);
        this.form.get("senderName")?.setValue(response.swv_Header.swv_sender_name);
        this.form.get("address1")?.setValue(response.swv_Header.swv_sender_address1);
        this.form.get("address2")?.setValue(response.swv_Header.swv_sender_address2);
        this.form.get("address3")?.setValue(response.swv_Header.swv_sender_address3);
        this.form.get("destination")?.setValue(response.swv_Header.swv_destination);
        this.form.get("transporter")?.setValue(response.swv_Header.swv_transporter);
        this.form.get("lrnumber")?.setValue(response.swv_Header.swv_lr_no);
        this.form.get("lrdate")?.setValue(response.swv_Header.swv_lr_dt);
        this.form.get("noOfCases")?.setValue(response.swv_Header.swv_cases);
        this.form.get("state")?.setValue(response.swv_Header.state_name);
        this.form.get("stateId")?.setValue(response.swv_Header.swv_state_id);
        this.form.get("displayMsg")?.setValue(response.swv_Header.swv_challan_msg);
        this.form.get("remarks")?.setValue(response.swv_Header.swv_remarks);
        this.file1 = response.swv_Header.swv_img1;
        this.file2 = response.swv_Header.swv_img2;
        this.medicoUtility.toggleProgressBar(false);
      });
    }
    addProduct() {
      this.form.get("action")?.setValue('S');
      this.mode = "ADD";
      this.getModProdList();
    }
    modify() {
      this.form.get("action")?.setValue('M');
      this.mode = "MODIFY";
      this.getModProdList();
    }
    delete() {
      this.form.get("action")?.setValue('D');
      this.mode = "DELETE";
      this.getModProdList();
    }
    getModProdList() {
      this.reset();
      this.medicoUtility.toggleProgressBar(true);
      this.stkWithdrawalService.getModProdList(this.form.get("action")?.value, this.form.get("sendLocId")?.value, this.form.get("headerId")?.value).subscribe(response => {
        this.prodList = response.prodList;
        this.medicoUtility.toggleProgressBar(false);
      });
    }
    getStockTypes() {
      if (this.form.get("productId")?.valid) {
        this.medicoUtility.toggleProgressBar(true);
        this.stkWithdrawalService.getStockTypes(this.form.get("action")?.value, this.form.get("productId")?.value, this.form.get("headerId")?.value).subscribe(response => {
          this.stockTypes = response.stockTypes;
          this.medicoUtility.toggleProgressBar(false);
        });
      }
    }
    getBatchDetailsByProdStkType(type) {
      this.batchList = new _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatTableDataSource();
      this.batchIds = new Array();
      this.batchStocks = new Array();
      this.detailIds = new Array();
      this.rates = new Array();
      this.batchWthQtys = new Array();
      this.batchCases = new Array();
      this.stkQtyAfterWth = new Array();
      if (type == 'P' && this.prodList) {
        for (let i = 0; i < this.prodList.length; i++) {
          if (this.form.get("productId")?.value == this.prodList[i].smp_prod_id) {
            this.stock_type = this.prodList[i].storage_type;
            this.form.get("stockType")?.setValue(this.stock_type.trim());
            //console.log("stock_type ::"+this.form.get("stockType")?.value);
          }
        }
      }
      if (this.form.get("productId")?.valid && this.form.get("stockType")?.valid) {
        this.medicoUtility.toggleProgressBar(true);
        this.stkWithdrawalService.getBatchDetailsByProdStkType(this.form.get("productId")?.value, this.form.get("stockType")?.value, this.form.get("sendLocId")?.value, this.form.get("headerId")?.value, this.form.get("action")?.value).subscribe(response => {
          this.batchList = response.batchList;
          this.batchStocks = response.batchStocks;
          this.batchIds = response.batchIds;
          this.detailIds = response.detailIds;
          this.rates = response.rates;
          //  this.stkQtyAfterWth=response.batchStocksAftWth;
          this.form.get("divId")?.setValue(response.divId);
          for (let i = 0; i < this.batchIds.length; i++) {
            if (this.form.get("action")?.value == 'M' || this.form.get("action")?.value == 'D') {
              this.batchWthQtys[i] = parseFloat(response.batchQtys[i]).toFixed(2);
              this.batchCases[i] = parseFloat(response.batchCases[i]).toFixed(2);
              this.stkQtyAfterWth[i] = parseFloat(response.batchStocksAftWth[i]).toFixed(2);
            } else {
              this.batchWthQtys[i] = parseFloat('0').toFixed(2);
              this.batchCases[i] = parseFloat('0').toFixed(2);
              this.stkQtyAfterWth[i] = parseFloat(response.batchStocks[i]).toFixed(2);
            }
          }
          this.medicoUtility.toggleProgressBar(false);
          // this.qtyToReduce.nativeElement.focus();
        });
      }
    }
    setRemainingStock($event, index) {
      var num = $event.target.value;
      if (parseFloat(num) < 0) {
        $event.target.value = 0;
        this.medicoService.popup("Error Message", "Negative Qty not allowed");
      } else {
        if (parseFloat(num) > parseFloat(this.batchStocks[index])) {
          $event.target.value = 0;
          this.medicoService.popup("Error Message", "Insufficient stock");
        } else {
          this.stkQtyAfterWth[index] = (parseFloat(this.batchStocks[index]) - parseFloat(num)).toFixed(2);
        }
      }
    }
    save() {
      if (this.form.get("action")?.value == 'D') {
        if (this.prodList && this.prodList.length > 1) {} else {
          this.medicoService.popup("Error Message", "Stock Withdrawal has only one item. You cannot delete it.");
          this.reset();
          return false;
        }
      }
      var flag = '1';
      for (let i = 0; i < this.batchWthQtys.length; i++) {
        if (parseFloat(this.batchWthQtys[i]) > 0) {
          flag = '0';
          break;
        }
      }
      if (flag == '1') {
        this.medicoService.popup("Error Message", "Withdrawn Qty selection is mandatory");
        return false;
      }
      this.form.get("currFinYear")?.setValue(this.session.CURR_FIN_YEAR);
      this.form.get("currPeriodCode")?.setValue(this.session.CURR_PERIOD);
      this.form.get("empId")?.setValue(this.session.EMP_ID);
      this.form.get("batchIds")?.setValue(this.batchIds);
      this.form.get("detailIds")?.setValue(this.detailIds);
      this.form.get("batchStocks")?.setValue(this.batchStocks);
      this.form.get("rates")?.setValue(this.rates);
      this.form.get("batchWthQtys")?.setValue(this.batchWthQtys);
      this.form.get("batchCases")?.setValue(this.batchCases);
      this.form.get("stkQtyAfterWth")?.setValue(this.stkQtyAfterWth);
      this.form.get("nlrdate")?.setValue(this.form.get("lrdate")?.value);
      this.form.get("nstkWithdrawalDate")?.setValue(this.form.get("stkWithdrawalDate")?.value);
      if (this.form.valid) {
        this.medicoUtility.toggleProgressBar(true);
        try {
          this.stkWithdrawalService.saveStkWithdrawal(this.form).subscribe(response => {
            //this.medicoService.openJustSnackBar(response.RESPONSE_MESSAGE, this.constants.snack_bar_milliseconds_8k);
            this.reset();
            this.medicoService.popup("Message", response.RESPONSE_MESSAGE);
            this.medicoUtility.toggleProgressBar(false);
            // this.product.nativeElement.focus();
            if (this.form.get("action")?.value == 'D') {
              this.getModProdList();
            }
          }, error => {
            this.medicoUtility.toggleProgressBar(false);
            this.medicoService.openJustSnackBar("Network Error Occurred!", this.constants.snack_bar_milliseconds_8k);
          });
        } catch (error) {
          this.medicoUtility.toggleProgressBar(false);
          this.medicoService.popup("Stock Withdrawal", "Network Error Occurred!");
        }
      } else {
        this.medicoUtility.validateAllFields(this.form);
        this.medicoUtility.toggleProgressBar(false);
      }
    }
    reset() {
      this.form.get('productId')?.reset();
      this.form.get('stockType')?.reset();
      this.batchList = new _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatTableDataSource();
      this.batchIds = new Array();
      this.batchStocks = new Array();
      this.detailIds = new Array();
      this.rates = new Array();
      this.batchWthQtys = new Array();
      this.batchCases = new Array();
      this.stkQtyAfterWth = new Array();
    }
    checkDetails() {
      if (this.headerId !== '0') {
        this.medicoUtility.toggleProgressBar(true);
        this.stkWithdrawalService.getSavedProdList(this.headerId).subscribe(response => {
          if (response.savedProdList.length > 0) {
            const dialogRef = this.dialog.open(StockWthModifyDetailComponent, {
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
    goBack() {
      window.history.back();
    }
    static ɵfac = function StkwithdrawalModifycontentComponent_Factory(__ngFactoryType__) {
      return new (__ngFactoryType__ || StkwithdrawalModifycontentComponent)(_angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵdirectiveInject"](_services_stk_withdrawal_stk_withdrawal_service__WEBPACK_IMPORTED_MODULE_1__.StkWithdrawalService), _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵdirectiveInject"](_classes_medico_utility__WEBPACK_IMPORTED_MODULE_2__.MedicoUtility), _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵdirectiveInject"](_services_medico_medico_service__WEBPACK_IMPORTED_MODULE_3__.MedicoService), _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵdirectiveInject"](_angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormBuilder), _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵdirectiveInject"](_angular_material_dialog__WEBPACK_IMPORTED_MODULE_8__.MatDialog), _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵdirectiveInject"](_angular_core__WEBPACK_IMPORTED_MODULE_5__.ElementRef), _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵdirectiveInject"](_angular_common__WEBPACK_IMPORTED_MODULE_9__.DatePipe), _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵdirectiveInject"](_angular_router__WEBPACK_IMPORTED_MODULE_10__.ActivatedRoute));
    };
    static ɵcmp = /*@__PURE__*/_angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵdefineComponent"]({
      type: StkwithdrawalModifycontentComponent,
      selectors: [["app-stkwithdrawal-modifycontent"]],
      viewQuery: function StkwithdrawalModifycontentComponent_Query(rf, ctx) {
        if (rf & 1) {
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵviewQuery"](_c0, 5);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵviewQuery"](_c1, 5);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵviewQuery"](_angular_material_paginator__WEBPACK_IMPORTED_MODULE_11__.MatPaginator, 5);
        }
        if (rf & 2) {
          let _t;
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵqueryRefresh"](_t = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵloadQuery"]()) && (ctx.qtyToReduce = _t.first);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵqueryRefresh"](_t = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵloadQuery"]()) && (ctx.product = _t.first);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵqueryRefresh"](_t = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵloadQuery"]()) && (ctx.paginator = _t.first);
        }
      },
      decls: 147,
      vars: 63,
      consts: [["address1", ""], ["address2", ""], ["address3", ""], ["product", ""], ["qtyToReduce", ""], [3, "formGroup"], [1, "pl-1", "pr-1", "pb-1", "container"], ["fxLayout", "row", "fxLayout.lt-md", "column"], ["fxFlex", "100%"], ["fxLayout", "row", "fxLayout.lt-md", "column", 1, "pl-1", "pt-1"], ["mat-raised-button", "", "type", "button", 3, "click"], ["fxFlex", "25%", "appearance", "outline", 3, "ngClass"], ["matInput", "", "type", "text", "formControlName", "stkWithdrawalNo", "readonly", "", 3, "tabindex"], ["matInput", "", "type", "text", "formControlName", "period", "readonly", "", 3, "tabindex"], ["matInput", "", "type", "text", "formControlName", "stkWithdrawalDate", "readonly", "", 3, "tabindex"], ["matInput", "", "type", "text", "formControlName", "stkWithdrawalType", "readonly", "", 3, "tabindex"], ["fxLayout", "row", "fxLayout.lt-md", "column", 1, "p-1", "pb-0"], ["fxFlex", "100%", 1, "purple-font", "bold-font", "help-title"], ["fxLayout", "row", "fxLayout.lt-md", "column", 1, "pl-1", "pt-0"], ["matInput", "", "type", "text", "formControlName", "senderName", "readonly", "", 3, "tabindex"], ["matInput", "", "type", "text", "formControlName", "address1", "readonly", "", 3, "tabindex"], ["matInput", "", "type", "text", "formControlName", "address2", "readonly", "", 3, "tabindex"], ["matInput", "", "type", "text", "formControlName", "address3", "readonly", "", 3, "tabindex"], ["matInput", "", "type", "text", "formControlName", "destination", "readonly", "", 3, "tabindex"], ["matInput", "", "type", "text", "formControlName", "transporter", "readonly", "", 3, "tabindex"], ["matInput", "", "type", "text", "formControlName", "lrnumber", "readonly", "", 3, "tabindex"], ["matInput", "", "type", "text", "formControlName", "lrdate", "readonly", "", 3, "tabindex"], ["matInput", "", "type", "text", "formControlName", "noOfCases", "readonly", "", 3, "tabindex"], ["matInput", "", "type", "text", "formControlName", "state", "readonly", "", 3, "tabindex"], ["matInput", "", "type", "text", "formControlName", "displayMsg", "readonly", "", 3, "tabindex"], ["matInput", "", "type", "text", "formControlName", "remarks", "readonly", "", 3, "tabindex"], ["mat-raised-button", "", "type", "button", 1, "mr-r-10", "color-3", "buttonHeight", 3, "click"], ["fxFlex", "50%", "appearance", "outline", 3, "ngClass"], ["tabindex", "1", "formControlName", "productId", "matNativeControl", "", "required", "", 3, "selectionChange", "ngClass"], [3, "matTooltip", "value", 4, "ngFor", "ngForOf"], ["tabindex", "2", "formControlName", "stockType", "matNativeControl", "", "required", "", 3, "selectionChange", "ngClass"], [3, "value", 4, "ngFor", "ngForOf"], [1, "table-container", "mat-elevation-z2", "width-only-100"], ["mat-table", "", "matSort", "", 1, "table-borders-right", "width-only-100", "table-th-bg-primary", "thin-th", "mat-td-p", "mat-th-p", 3, "dataSource"], ["matColumnDef", "BatchNo"], ["mat-header-cell", "", 4, "matHeaderCellDef"], ["mat-cell", "", 4, "matCellDef"], ["matColumnDef", "Stock"], ["mat-cell", "", "align", "right", 4, "matCellDef"], ["matColumnDef", "MfgDate"], ["matColumnDef", "Expiry"], ["matColumnDef", "qty"], ["matColumnDef", "cases"], ["matColumnDef", "rate"], ["matColumnDef", "stkAfterWth"], ["mat-header-row", "", "mat-sort-header", "", "class", "table-row1", 4, "matHeaderRowDef", "matHeaderRowDefSticky"], ["mat-row", "", "class", "table-row1", 4, "matRowDef", "matRowDefColumns"], ["showFirstLastButtons", "", 3, "pageSizeOptions"], ["fxLayout", "row", 1, "mt-1"], ["mat-raised-button", "", "type", "button", 1, "mr-r-10", "color-3", 3, "click"], ["mat-raised-button", "", "type", "button", 1, "mr-r-10", "color-4", 3, "click"], ["mat-raised-button", "", "type", "button", "routerLink", "/medico-user/home"], [3, "matTooltip", "value"], [3, "value"], ["mat-header-cell", ""], ["mat-cell", ""], ["mat-cell", "", "align", "right"], ["appNumbersOnly", "", "step", "0.01", "matInput", "", "type", "text", 1, "text-right", "mat-input-batdetails", 3, "ngModelChange", "change", "ngModel", "ngModelOptions"], ["appNumbersOnly", "", "step", "0.01", "matInput", "", "type", "text", 1, "text-right", "mat-input-batdetails", 3, "ngModelChange", "ngModel", "ngModelOptions"], ["mat-header-row", "", "mat-sort-header", "", 1, "table-row1"], ["mat-row", "", 1, "table-row1"]],
      template: function StkwithdrawalModifycontentComponent_Template(rf, ctx) {
        if (rf & 1) {
          const _r1 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵgetCurrentView"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "form", 5)(1, "div", 6)(2, "div", 7)(3, "div", 8)(4, "div", 9)(5, "button", 10);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵlistener"]("click", function StkwithdrawalModifycontentComponent_Template_button_click_5_listener() {
            _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵrestoreView"](_r1);
            return _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵresetView"](ctx.goBack());
          });
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](6, "Back");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](7, "mat-form-field", 11)(8, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](9, "Withdrawal Challan");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](10, "input", 12);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](11, "mat-form-field", 11)(12, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](13, "Period");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](14, "input", 13);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](15, "mat-form-field", 11)(16, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](17, "Withdrawal Date");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](18, "input", 14);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](19, "mat-form-field", 11)(20, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](21, "Withdrawal Type");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](22, "input", 15);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()()()();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](23, "div", 7)(24, "div", 8)(25, "div", 16)(26, "span", 17);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](27, "Send To");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](28, "div", 18)(29, "mat-form-field", 11)(30, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](31, "Name");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](32, "input", 19);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](33, "mat-form-field", 11)(34, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](35, "Address 1");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](36, "input", 20, 0);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](38, "mat-form-field", 11)(39, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](40, "Address 2");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](41, "input", 21, 1);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](43, "mat-form-field", 11)(44, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](45, "Address 3");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](46, "input", 22, 2);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](48, "div", 18)(49, "mat-form-field", 11)(50, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](51, "Destination");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](52, "input", 23);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](53, "mat-form-field", 11)(54, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](55, "Transporter");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](56, "input", 24);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](57, "mat-form-field", 11)(58, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](59, "LR Number");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](60, "input", 25);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](61, "mat-form-field", 11)(62, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](63, "LR Date");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](64, "input", 26);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](65, "div", 18)(66, "mat-form-field", 11)(67, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](68, "No Of Cases");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](69, "input", 27);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](70, "mat-form-field", 11)(71, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](72, "State");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](73, "input", 28);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](74, "mat-form-field", 11)(75, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](76, "Display Message");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](77, "input", 29);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](78, "mat-form-field", 11)(79, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](80, "Remarks");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](81, "input", 30);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()()()();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](82, "div", 7)(83, "div", 8)(84, "div", 16)(85, "span", 17);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](86, "Mode : ");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](87, "b");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](88);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()()();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](89, "div", 9)(90, "button", 31);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵlistener"]("click", function StkwithdrawalModifycontentComponent_Template_button_click_90_listener() {
            _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵrestoreView"](_r1);
            return _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵresetView"](ctx.addProduct());
          });
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](91, "Add");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](92, "button", 31);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵlistener"]("click", function StkwithdrawalModifycontentComponent_Template_button_click_92_listener() {
            _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵrestoreView"](_r1);
            return _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵresetView"](ctx.modify());
          });
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](93, "Modify");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](94, "button", 31);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵlistener"]("click", function StkwithdrawalModifycontentComponent_Template_button_click_94_listener() {
            _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵrestoreView"](_r1);
            return _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵresetView"](ctx.delete());
          });
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](95, "Delete");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](96, "mat-form-field", 32)(97, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](98, "Item Name");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](99, "mat-select", 33, 3);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵlistener"]("selectionChange", function StkwithdrawalModifycontentComponent_Template_mat_select_selectionChange_99_listener() {
            _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵrestoreView"](_r1);
            ctx.getStockTypes();
            return _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵresetView"](ctx.getBatchDetailsByProdStkType("P"));
          });
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](101, StkwithdrawalModifycontentComponent_mat_option_101_Template, 2, 4, "mat-option", 34);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](102, "mat-form-field", 32)(103, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](104, "Stock Type");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](105, "mat-select", 35);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵlistener"]("selectionChange", function StkwithdrawalModifycontentComponent_Template_mat_select_selectionChange_105_listener() {
            _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵrestoreView"](_r1);
            return _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵresetView"](ctx.getBatchDetailsByProdStkType("S"));
          });
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](106, StkwithdrawalModifycontentComponent_mat_option_106_Template, 2, 2, "mat-option", 36);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()()();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](107, "div", 37)(108, "table", 38);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](109, 39);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](110, StkwithdrawalModifycontentComponent_th_110_Template, 2, 0, "th", 40)(111, StkwithdrawalModifycontentComponent_td_111_Template, 2, 1, "td", 41);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](112, 42);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](113, StkwithdrawalModifycontentComponent_th_113_Template, 2, 0, "th", 40)(114, StkwithdrawalModifycontentComponent_td_114_Template, 2, 1, "td", 43);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](115, 44);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](116, StkwithdrawalModifycontentComponent_th_116_Template, 2, 0, "th", 40)(117, StkwithdrawalModifycontentComponent_td_117_Template, 2, 1, "td", 41);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](118, 45);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](119, StkwithdrawalModifycontentComponent_th_119_Template, 2, 0, "th", 40)(120, StkwithdrawalModifycontentComponent_td_120_Template, 2, 1, "td", 41);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](121, 46);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](122, StkwithdrawalModifycontentComponent_th_122_Template, 2, 0, "th", 40)(123, StkwithdrawalModifycontentComponent_td_123_Template, 3, 3, "td", 43);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](124, 47);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](125, StkwithdrawalModifycontentComponent_th_125_Template, 2, 0, "th", 40)(126, StkwithdrawalModifycontentComponent_td_126_Template, 2, 3, "td", 43);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](127, 48);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](128, StkwithdrawalModifycontentComponent_th_128_Template, 2, 0, "th", 40)(129, StkwithdrawalModifycontentComponent_td_129_Template, 2, 1, "td", 43);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](130, 49);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](131, StkwithdrawalModifycontentComponent_th_131_Template, 2, 0, "th", 40)(132, StkwithdrawalModifycontentComponent_td_132_Template, 2, 1, "td", 43);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](133, StkwithdrawalModifycontentComponent_tr_133_Template, 1, 0, "tr", 50)(134, StkwithdrawalModifycontentComponent_tr_134_Template, 1, 0, "tr", 51);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](135, "mat-paginator", 52);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()()();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](136, "div", 53)(137, "button", 10);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵlistener"]("click", function StkwithdrawalModifycontentComponent_Template_button_click_137_listener() {
            _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵrestoreView"](_r1);
            return _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵresetView"](ctx.save());
          });
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](138, "Save");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](139, "button", 54);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵlistener"]("click", function StkwithdrawalModifycontentComponent_Template_button_click_139_listener() {
            _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵrestoreView"](_r1);
            return _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵresetView"](ctx.reset());
          });
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](140, "Reset");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](141, "button", 55);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵlistener"]("click", function StkwithdrawalModifycontentComponent_Template_button_click_141_listener() {
            _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵrestoreView"](_r1);
            return _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵresetView"](ctx.checkDetails());
          });
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](142, "View");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](143, "button", 56);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](144, "Exit");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](145, "button", 54);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵlistener"]("click", function StkwithdrawalModifycontentComponent_Template_button_click_145_listener() {
            _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵrestoreView"](_r1);
            return _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵresetView"](ctx.viewFiles());
          });
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](146, "View Uploaded Files");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()()()();
        }
        if (rf & 2) {
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("formGroup", ctx.form);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](2);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵclassMap"](ctx.constants.page_div_class);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵclassMapInterpolate1"]("mr-r-10 ", ctx.constants.exit_btn_class, " buttonHeight");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](2);
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
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngClass", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("tabindex", -1);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵclassMap"](ctx.constants.page_div_class);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](6);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngClass", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("tabindex", -1);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngClass", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("tabindex", -1);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](2);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngClass", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("tabindex", -1);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](2);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngClass", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("tabindex", -1);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](3);
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
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngClass", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("tabindex", -1);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](2);
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
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngClass", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("tabindex", -1);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵclassMap"](ctx.constants.page_div_class);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](6);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](ctx.mode);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](8);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngClass", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngClass", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](2);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngForOf", ctx.prodList);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngClass", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngClass", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngForOf", ctx.stockTypes);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](2);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("dataSource", ctx.batchList);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](25);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("matHeaderRowDef", ctx.displayedColumns)("matHeaderRowDefSticky", true);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("matRowDefColumns", ctx.displayedColumns);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("pageSizeOptions", ctx.constants.paginator_upto_50);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](2);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵclassMapInterpolate1"]("mr-r-10 ", ctx.constants.save_btn_class, "");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](6);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵclassMapInterpolate1"]("mr-r-10 ", ctx.constants.exit_btn_class, "");
        }
      },
      dependencies: [_angular_router__WEBPACK_IMPORTED_MODULE_10__.RouterLink, _angular_common__WEBPACK_IMPORTED_MODULE_9__.NgClass, _angular_common__WEBPACK_IMPORTED_MODULE_9__.NgForOf, _angular_forms__WEBPACK_IMPORTED_MODULE_6__["ɵNgNoValidate"], _angular_forms__WEBPACK_IMPORTED_MODULE_6__.DefaultValueAccessor, _angular_forms__WEBPACK_IMPORTED_MODULE_6__.NgControlStatus, _angular_forms__WEBPACK_IMPORTED_MODULE_6__.NgControlStatusGroup, _angular_forms__WEBPACK_IMPORTED_MODULE_6__.RequiredValidator, _angular_forms__WEBPACK_IMPORTED_MODULE_6__.NgModel, _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormGroupDirective, _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControlName, _angular_material_form_field__WEBPACK_IMPORTED_MODULE_12__.MatFormField, _angular_material_form_field__WEBPACK_IMPORTED_MODULE_12__.MatLabel, _angular_material_button__WEBPACK_IMPORTED_MODULE_13__.MatButton, _angular_flex_layout_flex__WEBPACK_IMPORTED_MODULE_14__.DefaultLayoutDirective, _angular_flex_layout_flex__WEBPACK_IMPORTED_MODULE_14__.DefaultFlexDirective, _angular_flex_layout_extended__WEBPACK_IMPORTED_MODULE_15__.DefaultClassDirective, _angular_material_input__WEBPACK_IMPORTED_MODULE_16__.MatInput, _angular_material_select__WEBPACK_IMPORTED_MODULE_17__.MatSelect, _angular_material_core__WEBPACK_IMPORTED_MODULE_18__.MatOption, _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatTable, _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatHeaderCellDef, _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatHeaderRowDef, _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatColumnDef, _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatCellDef, _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatRowDef, _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatHeaderCell, _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatCell, _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatHeaderRow, _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatRow, _angular_material_paginator__WEBPACK_IMPORTED_MODULE_11__.MatPaginator, _angular_material_tooltip__WEBPACK_IMPORTED_MODULE_19__.MatTooltip, _directives_utility_numbers_only_directive__WEBPACK_IMPORTED_MODULE_4__.NumbersOnlyDirective],
      styles: ["/*# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IiIsInNvdXJjZVJvb3QiOiIifQ== */"]
    });
  }
  return StkwithdrawalModifycontentComponent;
})();
let StockWthModifyDetailComponent = /*#__PURE__*/(() => {
  class StockWthModifyDetailComponent {
    dialogRef;
    data;
    constructor(dialogRef, data) {
      this.dialogRef = dialogRef;
      this.data = data;
    }
    onNoClick() {
      this.dialogRef.close();
    }
    static ɵfac = function StockWthModifyDetailComponent_Factory(__ngFactoryType__) {
      return new (__ngFactoryType__ || StockWthModifyDetailComponent)(_angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵdirectiveInject"](_angular_material_dialog__WEBPACK_IMPORTED_MODULE_8__.MatDialogRef), _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵdirectiveInject"](_angular_material_dialog__WEBPACK_IMPORTED_MODULE_8__.MAT_DIALOG_DATA));
    };
    static ɵcmp = /*@__PURE__*/_angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵdefineComponent"]({
      type: StockWthModifyDetailComponent,
      selectors: [["stk-wth-detail-dialog"]],
      decls: 23,
      vars: 1,
      consts: [["mat-dialog-title", ""], ["mat-dialog-content", ""], [1, "mytable", "table-full-width"], [4, "ngFor", "ngForOf"], ["mat-dialog-actions", "", 1, "mt-1"], ["mat-raised-button", "", "color", "primary", "mat-dialog-close", ""], ["align", "right"]],
      template: function StockWthModifyDetailComponent_Template(rf, ctx) {
        if (rf & 1) {
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "h1", 0);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1, "Product Details");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](2, "div", 1)(3, "table", 2)(4, "thead")(5, "tr")(6, "th");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](7, "Item Name");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](8, "th");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](9, "Stock Type");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](10, "th");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](11, "Batch No");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](12, "th");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](13, "Qty");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](14, "th");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](15, "Cases");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](16, "th");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](17, "Rate");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()()();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](18, "tbody");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](19, StockWthModifyDetailComponent_tr_19_Template, 13, 6, "tr", 3);
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
  return StockWthModifyDetailComponent;
})();

/***/ }),

/***/ 12390:
/*!*************************************************************************!*\
  !*** ./src/app/components/transaction/stkwth-lr/stkwth-lr.component.ts ***!
  \*************************************************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   StkwthLrComponent: () => (/* binding */ StkwthLrComponent)
/* harmony export */ });
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! @angular/forms */ 34456);
/* harmony import */ var _classes_constants__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ../../../classes/constants */ 5556);
/* harmony import */ var _angular_material_table__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! @angular/material/table */ 77697);
/* harmony import */ var _angular_material_paginator__WEBPACK_IMPORTED_MODULE_11__ = __webpack_require__(/*! @angular/material/paginator */ 24624);
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! @angular/core */ 37580);
/* harmony import */ var _services_stk_withdrawal_stk_withdrawal_service__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ../../../services/stk-withdrawal/stk-withdrawal.service */ 74303);
/* harmony import */ var _classes_medico_utility__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ../../../classes/medico-utility */ 79955);
/* harmony import */ var _services_medico_medico_service__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ../../../services/medico/medico.service */ 353);
/* harmony import */ var _angular_material_dialog__WEBPACK_IMPORTED_MODULE_8__ = __webpack_require__(/*! @angular/material/dialog */ 12587);
/* harmony import */ var _angular_common__WEBPACK_IMPORTED_MODULE_9__ = __webpack_require__(/*! @angular/common */ 60316);
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_10__ = __webpack_require__(/*! @angular/router */ 95072);
/* harmony import */ var _angular_material_form_field__WEBPACK_IMPORTED_MODULE_12__ = __webpack_require__(/*! @angular/material/form-field */ 24950);
/* harmony import */ var _angular_material_button__WEBPACK_IMPORTED_MODULE_13__ = __webpack_require__(/*! @angular/material/button */ 84175);
/* harmony import */ var _angular_flex_layout_flex__WEBPACK_IMPORTED_MODULE_14__ = __webpack_require__(/*! @angular/flex-layout/flex */ 91447);
/* harmony import */ var _angular_flex_layout_extended__WEBPACK_IMPORTED_MODULE_15__ = __webpack_require__(/*! @angular/flex-layout/extended */ 52913);
/* harmony import */ var _angular_material_input__WEBPACK_IMPORTED_MODULE_16__ = __webpack_require__(/*! @angular/material/input */ 95541);
/* harmony import */ var _angular_material_checkbox__WEBPACK_IMPORTED_MODULE_17__ = __webpack_require__(/*! @angular/material/checkbox */ 97024);
/* harmony import */ var _angular_material_datepicker__WEBPACK_IMPORTED_MODULE_18__ = __webpack_require__(/*! @angular/material/datepicker */ 61977);
/* harmony import */ var _directives_utility_numbers_only_directive__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ../../../directives/utility/numbers-only.directive */ 22128);






















const _c0 = () => ({
  standalone: true
});
function StkwthLrComponent_th_42_Template(rf, ctx) {
  if (rf & 1) {
    const _r2 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵgetCurrentView"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "th", 36)(1, "mat-checkbox", 37);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtwoWayListener"]("ngModelChange", function StkwthLrComponent_th_42_Template_mat_checkbox_ngModelChange_1_listener($event) {
      _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵrestoreView"](_r2);
      const ctx_r2 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"]();
      _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtwoWayBindingSet"](ctx_r2.selectAlls, $event) || (ctx_r2.selectAlls = $event);
      return _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵresetView"]($event);
    });
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵlistener"]("change", function StkwthLrComponent_th_42_Template_mat_checkbox_change_1_listener() {
      _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵrestoreView"](_r2);
      const ctx_r2 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"]();
      return _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵresetView"](ctx_r2.selectAll());
    });
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    const ctx_r2 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtwoWayProperty"]("ngModel", ctx_r2.selectAlls);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngModelOptions", _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵpureFunction0"](2, _c0));
  }
}
function StkwthLrComponent_td_43_Template(rf, ctx) {
  if (rf & 1) {
    const _r4 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵgetCurrentView"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "td", 38)(1, "mat-checkbox", 39);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵlistener"]("change", function StkwthLrComponent_td_43_Template_mat_checkbox_change_1_listener() {
      const ctx_r4 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵrestoreView"](_r4);
      const row_r6 = ctx_r4.$implicit;
      const i_r7 = ctx_r4.index;
      const ctx_r2 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"]();
      return _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵresetView"](ctx_r2.checkCheckBoxvalue(row_r6[0], i_r7));
    });
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    const ctx_r2 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("checked", ctx_r2.checkedInd);
  }
}
function StkwthLrComponent_th_45_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "th", 40);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1, "Challan No");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
}
function StkwthLrComponent_td_46_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "td", 41);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const row_r8 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](row_r8[1]);
  }
}
function StkwthLrComponent_th_48_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "th", 42);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1, "Challan Date");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
}
function StkwthLrComponent_td_49_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "td", 43);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const row_r9 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](row_r9[2]);
  }
}
function StkwthLrComponent_th_51_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "th", 42);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1, "Challan Value");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
}
function StkwthLrComponent_td_52_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "td", 43);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const row_r10 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](row_r10[3].toFixed(2));
  }
}
function StkwthLrComponent_th_54_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "th", 42);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1, "Weight(Kgs.)");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
}
function StkwthLrComponent_td_55_Template(rf, ctx) {
  if (rf & 1) {
    const _r11 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵgetCurrentView"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "td", 43)(1, "input", 44);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtwoWayListener"]("ngModelChange", function StkwthLrComponent_td_55_Template_input_ngModelChange_1_listener($event) {
      const i_r12 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵrestoreView"](_r11).index;
      const ctx_r2 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"]();
      _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtwoWayBindingSet"](ctx_r2.weights[i_r12], $event) || (ctx_r2.weights[i_r12] = $event);
      return _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵresetView"]($event);
    });
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵlistener"]("change", function StkwthLrComponent_td_55_Template_input_change_1_listener($event) {
      const ctx_r12 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵrestoreView"](_r11);
      const row_r14 = ctx_r12.$implicit;
      const i_r12 = ctx_r12.index;
      const ctx_r2 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"]();
      return _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵresetView"](ctx_r2.setSvwLrNtWt($event, i_r12, row_r14[0]));
    });
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    const i_r12 = ctx.index;
    const ctx_r2 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtwoWayProperty"]("ngModel", ctx_r2.weights[i_r12]);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngModelOptions", _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵpureFunction0"](2, _c0));
  }
}
function StkwthLrComponent_th_57_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "th", 42);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1, "Cases");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
}
function StkwthLrComponent_td_58_Template(rf, ctx) {
  if (rf & 1) {
    const _r15 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵgetCurrentView"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "td", 43)(1, "input", 44);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtwoWayListener"]("ngModelChange", function StkwthLrComponent_td_58_Template_input_ngModelChange_1_listener($event) {
      const i_r16 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵrestoreView"](_r15).index;
      const ctx_r2 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"]();
      _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtwoWayBindingSet"](ctx_r2.cases[i_r16], $event) || (ctx_r2.cases[i_r16] = $event);
      return _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵresetView"]($event);
    });
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵlistener"]("change", function StkwthLrComponent_td_58_Template_input_change_1_listener($event) {
      const ctx_r16 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵrestoreView"](_r15);
      const row_r18 = ctx_r16.$implicit;
      const i_r16 = ctx_r16.index;
      const ctx_r2 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"]();
      return _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵresetView"](ctx_r2.setSvwLrNtCase($event, i_r16, row_r18[0]));
    });
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    const i_r16 = ctx.index;
    const ctx_r2 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtwoWayProperty"]("ngModel", ctx_r2.cases[i_r16]);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngModelOptions", _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵpureFunction0"](2, _c0));
  }
}
function StkwthLrComponent_tr_59_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](0, "tr", 45);
  }
}
function StkwthLrComponent_tr_60_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](0, "tr", 46);
  }
}
let StkwthLrComponent = /*#__PURE__*/(() => {
  class StkwthLrComponent {
    stkWithdrawalService;
    medicoUtility;
    medicoService;
    fb;
    dialog;
    el;
    datepipe;
    route;
    session;
    constants = new _classes_constants__WEBPACK_IMPORTED_MODULE_0__.Constants();
    checkedInd = false;
    selectAlls = false;
    ids = new Array();
    stkWthList;
    headerIds = new Array();
    weights = new Array();
    cases = new Array();
    totweights;
    totcases;
    displayedColumns = ["Select", "ChallanNo", "ChallanDate", "ChallanValue", "Weight", "Cases"];
    paginator;
    form;
    constructor(stkWithdrawalService, medicoUtility, medicoService, fb, dialog, el, datepipe, route) {
      this.stkWithdrawalService = stkWithdrawalService;
      this.medicoUtility = medicoUtility;
      this.medicoService = medicoService;
      this.fb = fb;
      this.dialog = dialog;
      this.el = el;
      this.datepipe = datepipe;
      this.route = route;
      this.form = this.fb.group({
        currPeriodCode: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        currFinYear: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        empId: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        sendSubCompId: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        sendLocId: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        sendLocation: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl("", _angular_forms__WEBPACK_IMPORTED_MODULE_6__.Validators.required),
        lrnumber: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl("", _angular_forms__WEBPACK_IMPORTED_MODULE_6__.Validators.required),
        lrdate: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl("", _angular_forms__WEBPACK_IMPORTED_MODULE_6__.Validators.required),
        transporter: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl("", _angular_forms__WEBPACK_IMPORTED_MODULE_6__.Validators.required),
        lorryNumber: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl("", _angular_forms__WEBPACK_IMPORTED_MODULE_6__.Validators.required),
        netWeight: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        totalCases: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        headerIds: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(0),
        weights: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(0),
        casess: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(0)
      });
      this.medicoService.headingName = "Stock Withdrawal LR Entry";
      this.medicoUtility.toggleProgressBar(true);
      this.session = this.medicoUtility.getSessionVariables();
      this.medicoUtility.toggleProgressBar(false);
      this.getDataForLrEntry();
    }
    ngOnInit() {}
    getDataForLrEntry() {
      this.medicoUtility.toggleProgressBar(true);
      this.stkWithdrawalService.getDataForStkWthLrEntry(this.session.EMP_ID).subscribe(response => {
        this.form.get("sendLocation")?.setValue(response.sendLocName);
        this.form.get("sendSubCompId")?.setValue(response.sendSubCompId);
        this.form.get("sendLocId")?.setValue(response.sendLocId);
        this.ids = response.headerIds;
        this.stkWthList = new _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatTableDataSource(response.list.slice(0));
        this.stkWthList.paginator = this.paginator;
        for (let i = 0; i < response.list.length; i++) {
          this.weights[i] = parseFloat('0').toFixed(2);
          this.cases[i] = parseFloat('0').toFixed(2);
        }
        this.totweights = '0';
        this.totcases = '0';
        this.medicoUtility.toggleProgressBar(false);
      });
    }
    selectAll() {
      if (this.selectAlls === true) {
        this.headerIds = this.ids;
        this.checkedInd = true;
      } else {
        this.headerIds = new Array();
        this.checkedInd = false;
      }
    }
    checkCheckBoxvalue(id, index) {
      if (this.selectAlls === true) {
        this.headerIds.splice(this.headerIds.indexOf(id), 1);
      } else {
        if (!this.headerIds.includes(id)) {
          this.headerIds.push(id);
        } else {
          this.headerIds.splice(this.headerIds.indexOf(id), 1);
        }
        //console.log(this.headerIds);
      }
    }
    // setSvwLrNtWt($event,index,id) {
    //   let weight=$event.target.value;
    //   if(weight !== '') {
    //       for (let i = 0; i < this.headerIds.length; i++) {
    //         if(this.headerIds[i] == id) {
    //         this.totweights=(parseFloat(this.totweights) + parseFloat(weight)).toFixed(2);
    //         } 
    //       }
    //       this.form.get("netWeight")?.setValue(this.totweights);
    //   }
    // }
    // setSvwLrNtCase($event,index,id) {
    //   let cases=$event.target.value;
    //   if(cases !== '') {
    //       for (let i = 0; i < this.headerIds.length; i++) {
    //         if(this.headerIds[i] == id) {
    //         this.totcases=(parseFloat(this.totcases) + parseFloat(cases)).toFixed(2);
    //         }
    //       }
    //       this.form.get("totalCases")?.setValue(this.totcases);
    //   }
    // }
    setSvwLrNtWt($event, index, id) {
      let weight = '0';
      for (let i = 0; i < this.weights.length; i++) {
        weight = (parseFloat(weight) + parseFloat(this.weights[i])).toFixed(2);
      }
      this.form.get("netWeight")?.setValue(weight);
    }
    setSvwLrNtCase($event, index, id) {
      let cases = '0';
      for (let i = 0; i < this.cases.length; i++) {
        cases = (parseFloat(cases) + parseFloat(this.cases[i])).toFixed(2);
      }
      this.form.get("totalCases")?.setValue(cases);
    }
    save() {
      this.form.get("currFinYear")?.setValue(this.session.CURR_FIN_YEAR);
      this.form.get("currPeriodCode")?.setValue(this.session.CURR_PERIOD);
      this.form.get("empId")?.setValue(this.session.EMP_ID);
      this.form.get("headerIds")?.setValue(this.headerIds);
      this.form.get("weights")?.setValue(this.weights);
      this.form.get("casess")?.setValue(this.cases);
      if (this.headerIds.length > 0) {
        this.medicoUtility.toggleProgressBar(true);
        try {
          console.log(this.form.value);
          if (this.form.valid) {
            this.form.get("lrdate")?.setValue(this.medicoUtility.formatDateTo_DD_MM_YYYY(this.form.get("lrdate")?.value));
            this.stkWithdrawalService.saveStkWithdrawalLr(this.form).subscribe(response => {
              if (response.DATA_SAVED) {
                this.medicoService.openJustSnackBar(response.RESPONSE_MESSAGE, this.constants.snack_bar_milliseconds_8k);
                this.form.reset();
                this.headerIds = new Array();
                this.weights = new Array();
                this.cases = new Array();
                this.stkWthList = new _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatTableDataSource();
                this.checkedInd = false;
                this.selectAlls = false;
                this.getDataForLrEntry();
                this.medicoUtility.toggleProgressBar(false);
              } else {
                this.medicoUtility.toggleProgressBar(false);
                this.medicoService.openJustSnackBar(response.RESPONSE_MESSAGE, this.constants.snack_bar_milliseconds_8k);
              }
            }, error => {
              this.medicoUtility.toggleProgressBar(false);
              this.medicoService.openJustSnackBar("Network Error Occurred!", this.constants.snack_bar_milliseconds_8k);
            });
          } else {
            this.medicoUtility.toggleProgressBar(false);
            this.medicoUtility.validateAllFields(this.form);
            this.medicoService.openJustSnackBar(this.constants.validate_all_fields_message, this.constants.snack_bar_milliseconds_4k);
          }
        } catch (error) {
          this.medicoUtility.toggleProgressBar(false);
          this.medicoService.openJustSnackBar("Network Error Occurred!", this.constants.snack_bar_milliseconds_8k);
        }
      }
    }
    static ɵfac = function StkwthLrComponent_Factory(__ngFactoryType__) {
      return new (__ngFactoryType__ || StkwthLrComponent)(_angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵdirectiveInject"](_services_stk_withdrawal_stk_withdrawal_service__WEBPACK_IMPORTED_MODULE_1__.StkWithdrawalService), _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵdirectiveInject"](_classes_medico_utility__WEBPACK_IMPORTED_MODULE_2__.MedicoUtility), _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵdirectiveInject"](_services_medico_medico_service__WEBPACK_IMPORTED_MODULE_3__.MedicoService), _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵdirectiveInject"](_angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormBuilder), _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵdirectiveInject"](_angular_material_dialog__WEBPACK_IMPORTED_MODULE_8__.MatDialog), _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵdirectiveInject"](_angular_core__WEBPACK_IMPORTED_MODULE_5__.ElementRef), _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵdirectiveInject"](_angular_common__WEBPACK_IMPORTED_MODULE_9__.DatePipe), _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵdirectiveInject"](_angular_router__WEBPACK_IMPORTED_MODULE_10__.ActivatedRoute));
    };
    static ɵcmp = /*@__PURE__*/_angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵdefineComponent"]({
      type: StkwthLrComponent,
      selectors: [["app-stkwth-lr"]],
      viewQuery: function StkwthLrComponent_Query(rf, ctx) {
        if (rf & 1) {
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵviewQuery"](_angular_material_paginator__WEBPACK_IMPORTED_MODULE_11__.MatPaginator, 5);
        }
        if (rf & 2) {
          let _t;
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵqueryRefresh"](_t = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵloadQuery"]()) && (ctx.paginator = _t.first);
        }
      },
      decls: 67,
      vars: 34,
      consts: [["lrdate", ""], [3, "formGroup"], [1, "pl-1", "pr-1", "pb-1", "container"], ["fxLayout", "row", "fxLayout.lt-md", "column"], ["fxFlex", "100%"], ["fxLayout", "row", "fxLayout.lt-md", "column", 1, "pl-1", "pt-1"], ["fxFlex", "25%", "appearance", "outline", 3, "ngClass"], ["matInput", "", "type", "text", "formControlName", "sendLocation", "readonly", "", "required", "", 3, "tabindex"], ["matInput", "", "type", "text", "formControlName", "lrnumber", "required", "", 3, "tabindex"], ["matInput", "", "placeholder", "LR Date", "required", "", "formControlName", "lrdate", "readonly", "", "required", "", 1, "mat-datepicker-input", 3, "matDatepicker"], ["matSuffix", "", 3, "for", "tabindex"], ["fxLayout", "row", "fxLayout.lt-md", "column", 1, "pl-1", "pt-0"], ["matInput", "", "type", "text", "formControlName", "transporter", "required", "", 3, "tabindex"], ["matInput", "", "type", "text", "formControlName", "lorryNumber", "required", "", 3, "tabindex"], ["matInput", "", "type", "text", "formControlName", "netWeight", 3, "tabindex"], ["matInput", "", "type", "text", "formControlName", "totalCases", 3, "tabindex"], [1, "table-container", "mat-elevation-z2", "width-only-100"], ["mat-table", "", "matSort", "", 1, "table-borders-right", "width-only-100", "table-th-bg-primary", "thin-th", "mat-td-p", "mat-th-p", 3, "dataSource"], ["matColumnDef", "Select", "sticky", ""], ["mat-header-cell", "", "class", "text-center", 4, "matHeaderCellDef"], ["mat-cell", "", "class", "text-center", 4, "matCellDef"], ["matColumnDef", "ChallanNo"], ["mat-header-cell", "", 4, "matHeaderCellDef"], ["mat-cell", "", 4, "matCellDef"], ["matColumnDef", "ChallanDate"], ["mat-header-cell", "", "class", "text-right", 4, "matHeaderCellDef"], ["mat-cell", "", "class", "text-right", 4, "matCellDef"], ["matColumnDef", "ChallanValue"], ["matColumnDef", "Weight"], ["matColumnDef", "Cases"], ["mat-header-row", "", "mat-sort-header", "", "class", "table-row1", 4, "matHeaderRowDef", "matHeaderRowDefSticky"], ["mat-row", "", "class", "table-row1", 4, "matRowDef", "matRowDefColumns"], ["showFirstLastButtons", "", 3, "pageSizeOptions"], ["fxLayout", "row", 1, "mt-1"], ["mat-raised-button", "", "type", "button", 3, "click"], ["mat-raised-button", "", "type", "button", "routerLink", "/medico-user/home"], ["mat-header-cell", "", 1, "text-center"], [3, "ngModelChange", "change", "ngModel", "ngModelOptions"], ["mat-cell", "", 1, "text-center"], ["value", "row[0]", 3, "change", "checked"], ["mat-header-cell", ""], ["mat-cell", ""], ["mat-header-cell", "", 1, "text-right"], ["mat-cell", "", 1, "text-right"], ["appNumbersOnly", "", "step", "0.01", "matInput", "", "type", "text", 1, "text-right", "mat-input-batdetails", 3, "ngModelChange", "change", "ngModel", "ngModelOptions"], ["mat-header-row", "", "mat-sort-header", "", 1, "table-row1"], ["mat-row", "", 1, "table-row1"]],
      template: function StkwthLrComponent_Template(rf, ctx) {
        if (rf & 1) {
          const _r1 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵgetCurrentView"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "form", 1)(1, "div", 2)(2, "div", 3)(3, "div", 4)(4, "div", 5)(5, "mat-form-field", 6)(6, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](7, "CFA Location");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](8, "input", 7);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](9, "mat-form-field", 6)(10, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](11, "LR Number");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](12, "input", 8);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](13, "mat-form-field", 6)(14, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](15, "LR Date");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](16, "input", 9)(17, "mat-datepicker-toggle", 10)(18, "mat-datepicker", null, 0);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](20, "div", 11)(21, "mat-form-field", 6)(22, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](23, "Transporter");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](24, "input", 12);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](25, "mat-form-field", 6)(26, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](27, "Lorry Number");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](28, "input", 13);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](29, "mat-form-field", 6)(30, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](31, "Net Weight(Kgs.)");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](32, "input", 14);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](33, "mat-form-field", 6)(34, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](35, "Total Cases");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](36, "input", 15);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()()()();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](37, "div", 3)(38, "div", 4)(39, "div", 16)(40, "table", 17);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](41, 18);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](42, StkwthLrComponent_th_42_Template, 2, 3, "th", 19)(43, StkwthLrComponent_td_43_Template, 2, 1, "td", 20);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](44, 21);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](45, StkwthLrComponent_th_45_Template, 2, 0, "th", 22)(46, StkwthLrComponent_td_46_Template, 2, 1, "td", 23);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](47, 24);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](48, StkwthLrComponent_th_48_Template, 2, 0, "th", 25)(49, StkwthLrComponent_td_49_Template, 2, 1, "td", 26);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](50, 27);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](51, StkwthLrComponent_th_51_Template, 2, 0, "th", 25)(52, StkwthLrComponent_td_52_Template, 2, 1, "td", 26);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](53, 28);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](54, StkwthLrComponent_th_54_Template, 2, 0, "th", 25)(55, StkwthLrComponent_td_55_Template, 2, 3, "td", 26);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](56, 29);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](57, StkwthLrComponent_th_57_Template, 2, 0, "th", 25)(58, StkwthLrComponent_td_58_Template, 2, 3, "td", 26);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](59, StkwthLrComponent_tr_59_Template, 1, 0, "tr", 30)(60, StkwthLrComponent_tr_60_Template, 1, 0, "tr", 31);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](61, "mat-paginator", 32);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()()();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](62, "div", 33)(63, "button", 34);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵlistener"]("click", function StkwthLrComponent_Template_button_click_63_listener() {
            _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵrestoreView"](_r1);
            return _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵresetView"](ctx.save());
          });
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](64, "Save");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](65, "button", 35);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](66, "Exit");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()()()();
        }
        if (rf & 2) {
          const lrdate_r19 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵreference"](19);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("formGroup", ctx.form);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](2);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵclassMap"](ctx.constants.page_div_class);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngClass", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("tabindex", -1);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngClass", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("tabindex", 1);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngClass", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("matDatepicker", lrdate_r19);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("for", lrdate_r19)("tabindex", 2);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](4);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngClass", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("tabindex", 3);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngClass", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("tabindex", 4);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngClass", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("tabindex", 5);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngClass", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("tabindex", 6);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵclassMap"](ctx.constants.page_div_class);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("dataSource", ctx.stkWthList);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](19);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("matHeaderRowDef", ctx.displayedColumns)("matHeaderRowDefSticky", true);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("matRowDefColumns", ctx.displayedColumns);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("pageSizeOptions", ctx.constants.paginator_upto_50);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](2);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵclassMapInterpolate1"]("mr-r-10 ", ctx.constants.save_btn_class, "");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](2);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵclassMapInterpolate1"]("mr-r-10 ", ctx.constants.exit_btn_class, "");
        }
      },
      dependencies: [_angular_router__WEBPACK_IMPORTED_MODULE_10__.RouterLink, _angular_common__WEBPACK_IMPORTED_MODULE_9__.NgClass, _angular_forms__WEBPACK_IMPORTED_MODULE_6__["ɵNgNoValidate"], _angular_forms__WEBPACK_IMPORTED_MODULE_6__.DefaultValueAccessor, _angular_forms__WEBPACK_IMPORTED_MODULE_6__.NgControlStatus, _angular_forms__WEBPACK_IMPORTED_MODULE_6__.NgControlStatusGroup, _angular_forms__WEBPACK_IMPORTED_MODULE_6__.RequiredValidator, _angular_forms__WEBPACK_IMPORTED_MODULE_6__.NgModel, _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormGroupDirective, _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControlName, _angular_material_form_field__WEBPACK_IMPORTED_MODULE_12__.MatFormField, _angular_material_form_field__WEBPACK_IMPORTED_MODULE_12__.MatLabel, _angular_material_form_field__WEBPACK_IMPORTED_MODULE_12__.MatSuffix, _angular_material_button__WEBPACK_IMPORTED_MODULE_13__.MatButton, _angular_flex_layout_flex__WEBPACK_IMPORTED_MODULE_14__.DefaultLayoutDirective, _angular_flex_layout_flex__WEBPACK_IMPORTED_MODULE_14__.DefaultFlexDirective, _angular_flex_layout_extended__WEBPACK_IMPORTED_MODULE_15__.DefaultClassDirective, _angular_material_input__WEBPACK_IMPORTED_MODULE_16__.MatInput, _angular_material_checkbox__WEBPACK_IMPORTED_MODULE_17__.MatCheckbox, _angular_material_datepicker__WEBPACK_IMPORTED_MODULE_18__.MatDatepicker, _angular_material_datepicker__WEBPACK_IMPORTED_MODULE_18__.MatDatepickerInput, _angular_material_datepicker__WEBPACK_IMPORTED_MODULE_18__.MatDatepickerToggle, _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatTable, _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatHeaderCellDef, _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatHeaderRowDef, _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatColumnDef, _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatCellDef, _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatRowDef, _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatHeaderCell, _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatCell, _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatHeaderRow, _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatRow, _angular_material_paginator__WEBPACK_IMPORTED_MODULE_11__.MatPaginator, _directives_utility_numbers_only_directive__WEBPACK_IMPORTED_MODULE_4__.NumbersOnlyDirective],
      styles: [".mat-input-batdetails[_ngcontent-%COMP%]{ width:90px !important;}\n.mat-form-field[_ngcontent-%COMP%] {width: auto !important;font-size: 10px;}\n.mat-select-value[_ngcontent-%COMP%] {max-width: 100%;width: auto;}  \n.table-row1[_ngcontent-%COMP%]{ height: 25px;}\n.mat-form-field-wrapper[_ngcontent-%COMP%] {padding-bottom:0px !important} \n.mat-datepicker-input[_ngcontent-%COMP%] {width: 85%; }\n  \nmat-icon[_ngcontent-%COMP%] {\n    position: relative;\n    float: right;\n    top: -6px;\n    cursor: pointer;\n    color: rgba(0, 0, 0, 0.54);\n    height: 2px;\n  }\n\n  .newcontainer[_ngcontent-%COMP%]{\n    height:800px;\n   }\n/*# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbIndlYnBhY2s6Ly8uL3NyYy9hcHAvY29tcG9uZW50cy90cmFuc2FjdGlvbi9zdGt3dGgtbHIvc3Rrd3RoLWxyLmNvbXBvbmVudC5jc3MiXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IkFBQUEsdUJBQXVCLHFCQUFxQixDQUFDO0FBQzdDLGlCQUFpQixzQkFBc0IsQ0FBQyxlQUFlLENBQUM7QUFDeEQsbUJBQW1CLGVBQWUsQ0FBQyxXQUFXLENBQUM7QUFDL0MsYUFBYSxZQUFZLENBQUM7QUFDMUIseUJBQXlCLDZCQUE2QjtBQUN0RCx1QkFBdUIsVUFBVSxFQUFFOztBQUVuQztJQUNJLGtCQUFrQjtJQUNsQixZQUFZO0lBQ1osU0FBUztJQUNULGVBQWU7SUFDZiwwQkFBMEI7SUFDMUIsV0FBVztFQUNiOztFQUVBO0lBQ0UsWUFBWTtHQUNiIiwic291cmNlc0NvbnRlbnQiOlsiLm1hdC1pbnB1dC1iYXRkZXRhaWxzeyB3aWR0aDo5MHB4ICFpbXBvcnRhbnQ7fVxyXG4ubWF0LWZvcm0tZmllbGQge3dpZHRoOiBhdXRvICFpbXBvcnRhbnQ7Zm9udC1zaXplOiAxMHB4O31cclxuLm1hdC1zZWxlY3QtdmFsdWUge21heC13aWR0aDogMTAwJTt3aWR0aDogYXV0bzt9ICBcclxuLnRhYmxlLXJvdzF7IGhlaWdodDogMjVweDt9XHJcbi5tYXQtZm9ybS1maWVsZC13cmFwcGVyIHtwYWRkaW5nLWJvdHRvbTowcHggIWltcG9ydGFudH0gXHJcbi5tYXQtZGF0ZXBpY2tlci1pbnB1dCB7d2lkdGg6IDg1JTsgfVxyXG4gIFxyXG5tYXQtaWNvbiB7XHJcbiAgICBwb3NpdGlvbjogcmVsYXRpdmU7XHJcbiAgICBmbG9hdDogcmlnaHQ7XHJcbiAgICB0b3A6IC02cHg7XHJcbiAgICBjdXJzb3I6IHBvaW50ZXI7XHJcbiAgICBjb2xvcjogcmdiYSgwLCAwLCAwLCAwLjU0KTtcclxuICAgIGhlaWdodDogMnB4O1xyXG4gIH1cclxuXHJcbiAgLm5ld2NvbnRhaW5lcntcclxuICAgIGhlaWdodDo4MDBweDtcclxuICAgfSJdLCJzb3VyY2VSb290IjoiIn0= */"]
    });
  }
  return StkwthLrComponent;
})();

/***/ }),

/***/ 92057:
/*!*************************************************!*\
  !*** ./src/app/modules/stkwithdrawal.module.ts ***!
  \*************************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   StkwithdrawalModule: () => (/* binding */ StkwithdrawalModule)
/* harmony export */ });
/* harmony import */ var _angular_common__WEBPACK_IMPORTED_MODULE_10__ = __webpack_require__(/*! @angular/common */ 60316);
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_9__ = __webpack_require__(/*! @angular/router */ 95072);
/* harmony import */ var _guards_authentication_guard_authentication_guard__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ../guards/authentication.guard/authentication.guard */ 40878);
/* harmony import */ var _child_essential_module__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./child.essential.module */ 62292);
/* harmony import */ var _directives_module__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./directives.module */ 25144);
/* harmony import */ var _components_transaction_stkwithdrawal_entry_stkwithdrawal_entry_component__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ../components/transaction/stkwithdrawal-entry/stkwithdrawal-entry.component */ 41014);
/* harmony import */ var _components_transaction_stkwithdrawal_modify_stkwithdrawal_modify_component__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ../components/transaction/stkwithdrawal-modify/stkwithdrawal-modify.component */ 85996);
/* harmony import */ var _components_transaction_stkwithdrawal_modifycontent_stkwithdrawal_modifycontent_component__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! ../components/transaction/stkwithdrawal-modifycontent/stkwithdrawal-modifycontent.component */ 21658);
/* harmony import */ var _components_approvals_stkwth_approval_stkwth_approval_component__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! ../components/approvals/stkwth-approval/stkwth-approval.component */ 7582);
/* harmony import */ var _components_transaction_stkwth_lr_stkwth_lr_component__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! ../components/transaction/stkwth-lr/stkwth-lr.component */ 12390);
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_8__ = __webpack_require__(/*! @angular/core */ 37580);












const routes = [{
  path: "entry",
  component: _components_transaction_stkwithdrawal_entry_stkwithdrawal_entry_component__WEBPACK_IMPORTED_MODULE_3__.StkwithdrawalEntryComponent,
  canActivate: [_guards_authentication_guard_authentication_guard__WEBPACK_IMPORTED_MODULE_0__.AuthenticationGuard]
}, {
  path: "modify",
  component: _components_transaction_stkwithdrawal_modify_stkwithdrawal_modify_component__WEBPACK_IMPORTED_MODULE_4__.StkwithdrawalModifyComponent,
  canActivate: [_guards_authentication_guard_authentication_guard__WEBPACK_IMPORTED_MODULE_0__.AuthenticationGuard]
}, {
  path: "modifyContent/:headerId",
  component: _components_transaction_stkwithdrawal_modifycontent_stkwithdrawal_modifycontent_component__WEBPACK_IMPORTED_MODULE_5__.StkwithdrawalModifycontentComponent,
  canActivate: [_guards_authentication_guard_authentication_guard__WEBPACK_IMPORTED_MODULE_0__.AuthenticationGuard]
}, {
  path: "self-approval",
  component: _components_approvals_stkwth_approval_stkwth_approval_component__WEBPACK_IMPORTED_MODULE_6__.StkwthApprovalComponent,
  canActivate: [_guards_authentication_guard_authentication_guard__WEBPACK_IMPORTED_MODULE_0__.AuthenticationGuard]
}, {
  path: "lr",
  component: _components_transaction_stkwth_lr_stkwth_lr_component__WEBPACK_IMPORTED_MODULE_7__.StkwthLrComponent,
  canActivate: [_guards_authentication_guard_authentication_guard__WEBPACK_IMPORTED_MODULE_0__.AuthenticationGuard]
}];
let StkwithdrawalModule = /*#__PURE__*/(() => {
  class StkwithdrawalModule {
    static ɵfac = function StkwithdrawalModule_Factory(__ngFactoryType__) {
      return new (__ngFactoryType__ || StkwithdrawalModule)();
    };
    static ɵmod = /*@__PURE__*/_angular_core__WEBPACK_IMPORTED_MODULE_8__["ɵɵdefineNgModule"]({
      type: StkwithdrawalModule
    });
    static ɵinj = /*@__PURE__*/_angular_core__WEBPACK_IMPORTED_MODULE_8__["ɵɵdefineInjector"]({
      imports: [_angular_router__WEBPACK_IMPORTED_MODULE_9__.RouterModule.forChild(routes), _angular_common__WEBPACK_IMPORTED_MODULE_10__.CommonModule, _child_essential_module__WEBPACK_IMPORTED_MODULE_1__.ChildEssentialsModule, _directives_module__WEBPACK_IMPORTED_MODULE_2__.DirectivesModule, _angular_router__WEBPACK_IMPORTED_MODULE_9__.RouterModule]
    });
  }
  return StkwithdrawalModule;
})();
(function () {
  (typeof ngJitMode === "undefined" || ngJitMode) && _angular_core__WEBPACK_IMPORTED_MODULE_8__["ɵɵsetNgModuleScope"](StkwithdrawalModule, {
    declarations: [_components_transaction_stkwithdrawal_entry_stkwithdrawal_entry_component__WEBPACK_IMPORTED_MODULE_3__.StkwithdrawalEntryComponent, _components_transaction_stkwithdrawal_entry_stkwithdrawal_entry_component__WEBPACK_IMPORTED_MODULE_3__.StockWthDetailComponent, _components_transaction_stkwithdrawal_modify_stkwithdrawal_modify_component__WEBPACK_IMPORTED_MODULE_4__.StkwithdrawalModifyComponent, _components_transaction_stkwithdrawal_modifycontent_stkwithdrawal_modifycontent_component__WEBPACK_IMPORTED_MODULE_5__.StkwithdrawalModifycontentComponent, _components_transaction_stkwithdrawal_modifycontent_stkwithdrawal_modifycontent_component__WEBPACK_IMPORTED_MODULE_5__.StockWthModifyDetailComponent, _components_approvals_stkwth_approval_stkwth_approval_component__WEBPACK_IMPORTED_MODULE_6__.StkwthApprovalComponent, _components_transaction_stkwth_lr_stkwth_lr_component__WEBPACK_IMPORTED_MODULE_7__.StkwthLrComponent],
    imports: [_angular_router__WEBPACK_IMPORTED_MODULE_9__.RouterModule, _angular_common__WEBPACK_IMPORTED_MODULE_10__.CommonModule, _child_essential_module__WEBPACK_IMPORTED_MODULE_1__.ChildEssentialsModule, _directives_module__WEBPACK_IMPORTED_MODULE_2__.DirectivesModule],
    exports: [_angular_router__WEBPACK_IMPORTED_MODULE_9__.RouterModule]
  });
})();

/***/ })

}]);
//# sourceMappingURL=57.js.map