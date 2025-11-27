"use strict";
(self["webpackChunkstk_cfa_angular"] = self["webpackChunkstk_cfa_angular"] || []).push([[92],{

/***/ 10694:
/*!*****************************************************************************************************!*\
  !*** ./src/app/components/transaction/annual-allocation-entry/annual-allocation-entry.component.ts ***!
  \*****************************************************************************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   AnnualAllocationEntryComponent: () => (/* binding */ AnnualAllocationEntryComponent),
/* harmony export */   AnnualDetailComponent: () => (/* binding */ AnnualDetailComponent)
/* harmony export */ });
/* harmony import */ var _classes_constants__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ../../../classes/constants */ 5556);
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! @angular/forms */ 34456);
/* harmony import */ var _angular_material_table__WEBPACK_IMPORTED_MODULE_8__ = __webpack_require__(/*! @angular/material/table */ 77697);
/* harmony import */ var _angular_material_paginator__WEBPACK_IMPORTED_MODULE_10__ = __webpack_require__(/*! @angular/material/paginator */ 24624);
/* harmony import */ var _angular_material_dialog__WEBPACK_IMPORTED_MODULE_9__ = __webpack_require__(/*! @angular/material/dialog */ 12587);
/* harmony import */ var _angular_material_sort__WEBPACK_IMPORTED_MODULE_21__ = __webpack_require__(/*! @angular/material/sort */ 22047);
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! @angular/core */ 37580);
/* harmony import */ var _classes_medico_utility__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ../../../classes/medico-utility */ 79955);
/* harmony import */ var _services_medico_medico_service__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ../../../services/medico/medico.service */ 353);
/* harmony import */ var src_app_services_allocation_allocation_service__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! src/app/services/allocation/allocation.service */ 81235);
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_11__ = __webpack_require__(/*! @angular/router */ 95072);
/* harmony import */ var _angular_common__WEBPACK_IMPORTED_MODULE_12__ = __webpack_require__(/*! @angular/common */ 60316);
/* harmony import */ var _angular_material_form_field__WEBPACK_IMPORTED_MODULE_13__ = __webpack_require__(/*! @angular/material/form-field */ 24950);
/* harmony import */ var _angular_flex_layout_flex__WEBPACK_IMPORTED_MODULE_14__ = __webpack_require__(/*! @angular/flex-layout/flex */ 91447);
/* harmony import */ var _angular_flex_layout_extended__WEBPACK_IMPORTED_MODULE_15__ = __webpack_require__(/*! @angular/flex-layout/extended */ 52913);
/* harmony import */ var _angular_material_input__WEBPACK_IMPORTED_MODULE_16__ = __webpack_require__(/*! @angular/material/input */ 95541);
/* harmony import */ var _angular_material_expansion__WEBPACK_IMPORTED_MODULE_17__ = __webpack_require__(/*! @angular/material/expansion */ 19322);
/* harmony import */ var _angular_material_select__WEBPACK_IMPORTED_MODULE_18__ = __webpack_require__(/*! @angular/material/select */ 25175);
/* harmony import */ var _angular_material_core__WEBPACK_IMPORTED_MODULE_19__ = __webpack_require__(/*! @angular/material/core */ 74646);
/* harmony import */ var _angular_material_tooltip__WEBPACK_IMPORTED_MODULE_20__ = __webpack_require__(/*! @angular/material/tooltip */ 80640);
/* harmony import */ var _directives_utility_numbers_only_directive__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ../../../directives/utility/numbers-only.directive */ 22128);
/* harmony import */ var src_app_services_user_user_service__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! src/app/services/user/user.service */ 83273);

























const _c0 = () => ["productCode_", "productName_", "year_", "presenceIndia_", "sampleType_", "cog_", "previousYearDispatch", "currentYearPlan", "excess_"];
const _c1 = (a0, a1) => [a0, a1];
const _c2 = (a0, a1, a2, a3) => [a0, a1, a2, a3];
const _c3 = () => ({
  "display": "none"
});
const _c4 = () => ({
  standalone: true
});
const _c5 = () => ["currQuarter_", "currMonth_", "currPerPso_", "currTotatUnits_", "currTotalCost_", "previousYearFigures"];
function AnnualAllocationEntryComponent_mat_option_33_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "mat-option", 16);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const m_r1 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("value", _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵpureFunction2"](2, _c1, m_r1.mgr_id, m_r1.mgrEmpId));
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtextInterpolate"](m_r1.mgr_name);
  }
}
function AnnualAllocationEntryComponent_mat_option_38_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "mat-option", 16);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const t_r2 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("value", _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵpureFunction4"](2, _c2, t_r2.ediv_div_id, t_r2.fm_cnt, t_r2.levelName, t_r2.team_req_ind));
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtextInterpolate"](t_r2.div_disp_nm);
  }
}
function AnnualAllocationEntryComponent_mat_option_43_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "mat-option", 16);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const t_r3 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("value", t_r3.team_code);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtextInterpolate1"]("", t_r3.team_name, " ");
  }
}
function AnnualAllocationEntryComponent_mat_option_48_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "mat-option", 16);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const p_r4 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("value", p_r4.smp_prod_type_id);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtextInterpolate"](p_r4.sgprmdet_disp_nm);
  }
}
function AnnualAllocationEntryComponent_mat_option_55_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "mat-option", 16);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const b_r5 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("value", b_r5.smp_sa_prod_group);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtextInterpolate"](b_r5.sa_group_name);
  }
}
function AnnualAllocationEntryComponent_mat_option_60_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "mat-option", 16);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const l_r6 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("value", l_r6.key);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtextInterpolate"](l_r6.value);
  }
}
function AnnualAllocationEntryComponent_th_65_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelement"](0, "th", 61);
  }
  if (rf & 2) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("ngStyle", _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵpureFunction0"](1, _c3));
  }
}
function AnnualAllocationEntryComponent_td_66_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "td", 62);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const ctx_r6 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵnextContext"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵpropertyInterpolate"]("matTooltip", ctx_r6.productCode);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtextInterpolate1"]("", ctx_r6.productCode, " ");
  }
}
function AnnualAllocationEntryComponent_th_68_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "th", 63)(1, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](2, "F Code");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵattribute"]("rowspan", 2);
  }
}
function AnnualAllocationEntryComponent_th_70_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelement"](0, "th", 61);
  }
  if (rf & 2) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("ngStyle", _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵpureFunction0"](1, _c3));
  }
}
function AnnualAllocationEntryComponent_td_71_mat_option_5_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "mat-option", 68);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const p_r9 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵpropertyInterpolate"]("matTooltip", p_r9.smp_prod_name);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("value", p_r9.smp_prod_id);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtextInterpolate"](p_r9.smp_prod_name);
  }
}
function AnnualAllocationEntryComponent_td_71_Template(rf, ctx) {
  if (rf & 1) {
    const _r8 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵgetCurrentView"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "td", 64)(1, "mat-form-field", 65)(2, "mat-label");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](3, "Select");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](4, "mat-select", 66);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵlistener"]("selectionChange", function AnnualAllocationEntryComponent_td_71_Template_mat_select_selectionChange_4_listener() {
      _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵrestoreView"](_r8);
      const ctx_r6 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵnextContext"]();
      return _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵresetView"](ctx_r6.setPreviousYearDetails());
    });
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](5, AnnualAllocationEntryComponent_td_71_mat_option_5_Template, 2, 3, "mat-option", 67);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()()();
  }
  if (rf & 2) {
    const row_r10 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"](5);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("ngForOf", row_r10.productList);
  }
}
function AnnualAllocationEntryComponent_th_73_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "th", 63)(1, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](2, "Product Name");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵattribute"]("rowspan", 2);
  }
}
function AnnualAllocationEntryComponent_th_75_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelement"](0, "th", 61);
  }
  if (rf & 2) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("ngStyle", _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵpureFunction0"](1, _c3));
  }
}
function AnnualAllocationEntryComponent_td_76_mat_option_3_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "mat-option", 16);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const l_r12 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("value", l_r12.key);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtextInterpolate"](l_r12.value);
  }
}
function AnnualAllocationEntryComponent_td_76_Template(rf, ctx) {
  if (rf & 1) {
    const _r11 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵgetCurrentView"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "td", 64)(1, "mat-form-field", 69)(2, "mat-select", 70);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵlistener"]("selectionChange", function AnnualAllocationEntryComponent_td_76_Template_mat_select_selectionChange_2_listener() {
      _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵrestoreView"](_r11);
      const ctx_r6 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵnextContext"]();
      return _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵresetView"](ctx_r6.setPreviousYearDetails());
    });
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](3, AnnualAllocationEntryComponent_td_76_mat_option_3_Template, 2, 2, "mat-option", 10);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](4, "input", 71);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵlistener"]("click", function AnnualAllocationEntryComponent_td_76_Template_input_click_4_listener() {
      _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵrestoreView"](_r11);
      const ctx_r6 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵnextContext"]();
      return _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵresetView"](ctx_r6.copyFromPreviousYear());
    });
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    const ctx_r6 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵnextContext"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"](3);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("ngForOf", ctx_r6.copyAllocation);
  }
}
function AnnualAllocationEntryComponent_th_78_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "th", 63)(1, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](2, "Copy From");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵattribute"]("rowspan", 2);
  }
}
function AnnualAllocationEntryComponent_th_80_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelement"](0, "th", 61);
  }
  if (rf & 2) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("ngStyle", _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵpureFunction0"](1, _c3));
  }
}
function AnnualAllocationEntryComponent_td_81_Template(rf, ctx) {
  if (rf & 1) {
    const _r13 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵgetCurrentView"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "td", 64)(1, "input", 72);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtwoWayListener"]("ngModelChange", function AnnualAllocationEntryComponent_td_81_Template_input_ngModelChange_1_listener($event) {
      _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵrestoreView"](_r13);
      const ctx_r6 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵnextContext"]();
      _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtwoWayBindingSet"](ctx_r6.presentInIndia, $event) || (ctx_r6.presentInIndia = $event);
      return _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵresetView"]($event);
    });
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵlistener"]("change", function AnnualAllocationEntryComponent_td_81_Template_input_change_1_listener($event) {
      const i_r14 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵrestoreView"](_r13).$implicit;
      const ctx_r6 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵnextContext"]();
      return _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵresetView"](ctx_r6.claculateFields($event, i_r14));
    });
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    const ctx_r6 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵnextContext"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtwoWayProperty"]("ngModel", ctx_r6.presentInIndia);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("ngModelOptions", _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵpureFunction0"](2, _c4));
  }
}
function AnnualAllocationEntryComponent_th_83_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "th", 63)(1, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](2, "In India");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵattribute"]("rowspan", 2);
  }
}
function AnnualAllocationEntryComponent_th_85_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelement"](0, "th", 61);
  }
  if (rf & 2) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("ngStyle", _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵpureFunction0"](1, _c3));
  }
}
function AnnualAllocationEntryComponent_td_86_Template(rf, ctx) {
  if (rf & 1) {
    const _r15 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵgetCurrentView"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "td", 64)(1, "input", 73);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtwoWayListener"]("ngModelChange", function AnnualAllocationEntryComponent_td_86_Template_input_ngModelChange_1_listener($event) {
      _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵrestoreView"](_r15);
      const ctx_r6 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵnextContext"]();
      _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtwoWayBindingSet"](ctx_r6.sampleType, $event) || (ctx_r6.sampleType = $event);
      return _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵresetView"]($event);
    });
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵlistener"]("change", function AnnualAllocationEntryComponent_td_86_Template_input_change_1_listener($event) {
      const i_r16 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵrestoreView"](_r15).$implicit;
      const ctx_r6 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵnextContext"]();
      return _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵresetView"](ctx_r6.claculateFields($event, i_r16));
    });
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    const ctx_r6 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵnextContext"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtwoWayProperty"]("ngModel", ctx_r6.sampleType);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("ngModelOptions", _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵpureFunction0"](2, _c4));
  }
}
function AnnualAllocationEntryComponent_th_88_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "th", 63)(1, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](2, "PGS / Conv.");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵattribute"]("rowspan", 2);
  }
}
function AnnualAllocationEntryComponent_th_90_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelement"](0, "th", 61);
  }
  if (rf & 2) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("ngStyle", _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵpureFunction0"](1, _c3));
  }
}
function AnnualAllocationEntryComponent_td_91_Template(rf, ctx) {
  if (rf & 1) {
    const _r17 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵgetCurrentView"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "td", 64)(1, "input", 74);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtwoWayListener"]("ngModelChange", function AnnualAllocationEntryComponent_td_91_Template_input_ngModelChange_1_listener($event) {
      _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵrestoreView"](_r17);
      const ctx_r6 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵnextContext"]();
      _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtwoWayBindingSet"](ctx_r6.cog, $event) || (ctx_r6.cog = $event);
      return _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵresetView"]($event);
    });
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵlistener"]("change", function AnnualAllocationEntryComponent_td_91_Template_input_change_1_listener($event) {
      const i_r18 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵrestoreView"](_r17).$implicit;
      const ctx_r6 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵnextContext"]();
      return _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵresetView"](ctx_r6.claculateFields($event, i_r18));
    });
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    const ctx_r6 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵnextContext"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtwoWayProperty"]("ngModel", ctx_r6.cog);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("ngModelOptions", _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵpureFunction0"](2, _c4));
  }
}
function AnnualAllocationEntryComponent_th_93_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "th", 63)(1, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](2, "COG");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵattribute"]("rowspan", 2);
  }
}
function AnnualAllocationEntryComponent_th_95_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "th", 75)(1, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](2, "Total Units");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()();
  }
}
function AnnualAllocationEntryComponent_td_96_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "td", 76);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const ctx_r6 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵnextContext"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtextInterpolate"](ctx_r6.prevDspUnit);
  }
}
function AnnualAllocationEntryComponent_th_98_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "th", 75)(1, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](2, "Team Size");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()();
  }
}
function AnnualAllocationEntryComponent_td_99_Template(rf, ctx) {
  if (rf & 1) {
    const _r19 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵgetCurrentView"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "td", 64)(1, "input", 77);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtwoWayListener"]("ngModelChange", function AnnualAllocationEntryComponent_td_99_Template_input_ngModelChange_1_listener($event) {
      _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵrestoreView"](_r19);
      const ctx_r6 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵnextContext"]();
      _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtwoWayBindingSet"](ctx_r6.prevDspCount, $event) || (ctx_r6.prevDspCount = $event);
      return _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵresetView"]($event);
    });
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    const ctx_r6 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵnextContext"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtwoWayProperty"]("ngModel", ctx_r6.prevDspCount);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("ngModelOptions", _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵpureFunction0"](2, _c4));
  }
}
function AnnualAllocationEntryComponent_th_101_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "th", 75)(1, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](2, "Act. Team Size");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()();
  }
}
function AnnualAllocationEntryComponent_td_102_Template(rf, ctx) {
  if (rf & 1) {
    const _r20 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵgetCurrentView"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "td", 64)(1, "input", 77);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtwoWayListener"]("ngModelChange", function AnnualAllocationEntryComponent_td_102_Template_input_ngModelChange_1_listener($event) {
      _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵrestoreView"](_r20);
      const ctx_r6 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵnextContext"]();
      _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtwoWayBindingSet"](ctx_r6.prevActualTeamCount, $event) || (ctx_r6.prevActualTeamCount = $event);
      return _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵresetView"]($event);
    });
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    const ctx_r6 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵnextContext"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtwoWayProperty"]("ngModel", ctx_r6.prevActualTeamCount);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("ngModelOptions", _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵpureFunction0"](2, _c4));
  }
}
function AnnualAllocationEntryComponent_th_104_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "th", 75)(1, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    const ctx_r6 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵnextContext"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtextInterpolate1"]("Per ", ctx_r6.levelName, "");
  }
}
function AnnualAllocationEntryComponent_td_105_Template(rf, ctx) {
  if (rf & 1) {
    const _r21 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵgetCurrentView"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "td", 76)(1, "input", 77);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtwoWayListener"]("ngModelChange", function AnnualAllocationEntryComponent_td_105_Template_input_ngModelChange_1_listener($event) {
      _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵrestoreView"](_r21);
      const ctx_r6 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵnextContext"]();
      _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtwoWayBindingSet"](ctx_r6.prevPerPso, $event) || (ctx_r6.prevPerPso = $event);
      return _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵresetView"]($event);
    });
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    const ctx_r6 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵnextContext"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtwoWayProperty"]("ngModel", ctx_r6.prevPerPso);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("ngModelOptions", _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵpureFunction0"](2, _c4));
  }
}
function AnnualAllocationEntryComponent_th_107_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "th", 75)(1, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](2, "Total Units");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()();
  }
}
function AnnualAllocationEntryComponent_td_108_Template(rf, ctx) {
  if (rf & 1) {
    const _r22 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵgetCurrentView"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "td", 64)(1, "input", 77);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtwoWayListener"]("ngModelChange", function AnnualAllocationEntryComponent_td_108_Template_input_ngModelChange_1_listener($event) {
      _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵrestoreView"](_r22);
      const ctx_r6 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵnextContext"]();
      _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtwoWayBindingSet"](ctx_r6.currTotalUnitsFinal, $event) || (ctx_r6.currTotalUnitsFinal = $event);
      return _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵresetView"]($event);
    });
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    const ctx_r6 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵnextContext"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtwoWayProperty"]("ngModel", ctx_r6.currTotalUnitsFinal);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("ngModelOptions", _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵpureFunction0"](2, _c4));
  }
}
function AnnualAllocationEntryComponent_th_110_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "th", 75)(1, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](2, "Team Size");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()();
  }
}
function AnnualAllocationEntryComponent_td_111_Template(rf, ctx) {
  if (rf & 1) {
    const _r23 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵgetCurrentView"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "td", 64)(1, "input", 77);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtwoWayListener"]("ngModelChange", function AnnualAllocationEntryComponent_td_111_Template_input_ngModelChange_1_listener($event) {
      _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵrestoreView"](_r23);
      const ctx_r6 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵnextContext"]();
      _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtwoWayBindingSet"](ctx_r6.teamSize, $event) || (ctx_r6.teamSize = $event);
      return _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵresetView"]($event);
    });
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    const ctx_r6 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵnextContext"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtwoWayProperty"]("ngModel", ctx_r6.teamSize);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("ngModelOptions", _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵpureFunction0"](2, _c4));
  }
}
function AnnualAllocationEntryComponent_th_113_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "th", 75)(1, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    const ctx_r6 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵnextContext"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtextInterpolate1"]("Per ", ctx_r6.levelName, "");
  }
}
function AnnualAllocationEntryComponent_td_114_Template(rf, ctx) {
  if (rf & 1) {
    const _r24 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵgetCurrentView"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "td", 64)(1, "input", 77);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtwoWayListener"]("ngModelChange", function AnnualAllocationEntryComponent_td_114_Template_input_ngModelChange_1_listener($event) {
      _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵrestoreView"](_r24);
      const ctx_r6 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵnextContext"]();
      _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtwoWayBindingSet"](ctx_r6.currPerPsoFinal, $event) || (ctx_r6.currPerPsoFinal = $event);
      return _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵresetView"]($event);
    });
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    const ctx_r6 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵnextContext"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtwoWayProperty"]("ngModel", ctx_r6.currPerPsoFinal);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("ngModelOptions", _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵpureFunction0"](2, _c4));
  }
}
function AnnualAllocationEntryComponent_th_116_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelement"](0, "th", 61);
  }
  if (rf & 2) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("ngStyle", _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵpureFunction0"](1, _c3));
  }
}
function AnnualAllocationEntryComponent_td_117_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "td", 78);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const ctx_r6 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵnextContext"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtextInterpolate"](ctx_r6.excessDeficitPer.toFixed(2));
  }
}
function AnnualAllocationEntryComponent_th_119_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "th", 63)(1, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](2, "+/- %");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵattribute"]("rowspan", 2);
  }
}
function AnnualAllocationEntryComponent_th_121_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "th", 75)(1, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](2, "Previous Year Dispatched");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵattribute"]("colspan", 4);
  }
}
function AnnualAllocationEntryComponent_th_123_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "th", 75)(1, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](2, "Current Year Planned");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵattribute"]("colspan", 3);
  }
}
function AnnualAllocationEntryComponent_tr_124_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelement"](0, "tr", 79);
  }
}
function AnnualAllocationEntryComponent_tr_125_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelement"](0, "tr", 80);
  }
}
function AnnualAllocationEntryComponent_tr_126_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelement"](0, "tr", 81);
  }
}
function AnnualAllocationEntryComponent_div_127_th_4_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelement"](0, "th", 61);
  }
  if (rf & 2) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("ngStyle", _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵpureFunction0"](1, _c3));
  }
}
function AnnualAllocationEntryComponent_div_127_td_5_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "td", 64)(1, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    const i_r25 = ctx.index;
    const ctx_r6 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵnextContext"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵstyleProp"]("display", ctx_r6.getRowSpan(i_r25) ? "" : "none");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵattribute"]("rowspan", ctx_r6.getRowSpan(i_r25));
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtextInterpolate1"]("Quarter-", i_r25 / 3 + 1, "");
  }
}
function AnnualAllocationEntryComponent_div_127_td_6_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "td", 108);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](1, "Total");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
  }
}
function AnnualAllocationEntryComponent_div_127_th_8_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "th", 63)(1, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](2, "Quarter");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵattribute"]("rowspan", 2);
  }
}
function AnnualAllocationEntryComponent_div_127_th_10_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelement"](0, "th", 61);
  }
  if (rf & 2) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("ngStyle", _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵpureFunction0"](1, _c3));
  }
}
function AnnualAllocationEntryComponent_div_127_td_11_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "td", 64)(1, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    const row_r26 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtextInterpolate"](row_r26.period_name);
  }
}
function AnnualAllocationEntryComponent_div_127_td_12_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelement"](0, "td", 108);
  }
}
function AnnualAllocationEntryComponent_div_127_th_14_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "th", 63)(1, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](2, "Month");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵattribute"]("rowspan", 2);
  }
}
function AnnualAllocationEntryComponent_div_127_th_16_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelement"](0, "th", 61);
  }
  if (rf & 2) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("ngStyle", _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵpureFunction0"](1, _c3));
  }
}
function AnnualAllocationEntryComponent_div_127_td_17_Template(rf, ctx) {
  if (rf & 1) {
    const _r27 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵgetCurrentView"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "td", 109)(1, "input", 110);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtwoWayListener"]("ngModelChange", function AnnualAllocationEntryComponent_div_127_td_17_Template_input_ngModelChange_1_listener($event) {
      const i_r28 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵrestoreView"](_r27).index;
      const ctx_r6 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵnextContext"](2);
      _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtwoWayBindingSet"](ctx_r6.currPerPso[i_r28], $event) || (ctx_r6.currPerPso[i_r28] = $event);
      return _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵresetView"]($event);
    });
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵlistener"]("change", function AnnualAllocationEntryComponent_div_127_td_17_Template_input_change_1_listener($event) {
      const i_r28 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵrestoreView"](_r27).index;
      const ctx_r6 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵnextContext"](2);
      return _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵresetView"](ctx_r6.claculateFields($event, i_r28));
    });
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    const row_r29 = ctx.$implicit;
    const i_r28 = ctx.index;
    const ctx_r6 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵnextContext"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("value", row_r29.currPerPso);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtwoWayProperty"]("ngModel", ctx_r6.currPerPso[i_r28]);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("ngModelOptions", _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵpureFunction0"](3, _c4));
  }
}
function AnnualAllocationEntryComponent_div_127_td_18_Template(rf, ctx) {
  if (rf & 1) {
    const _r30 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵgetCurrentView"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "td", 108)(1, "input", 111);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtwoWayListener"]("ngModelChange", function AnnualAllocationEntryComponent_div_127_td_18_Template_input_ngModelChange_1_listener($event) {
      _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵrestoreView"](_r30);
      const ctx_r6 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵnextContext"](2);
      _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtwoWayBindingSet"](ctx_r6.currPerPsoFinal, $event) || (ctx_r6.currPerPsoFinal = $event);
      return _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵresetView"]($event);
    });
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    const ctx_r6 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵnextContext"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtwoWayProperty"]("ngModel", ctx_r6.currPerPsoFinal);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("ngModelOptions", _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵpureFunction0"](2, _c4));
  }
}
function AnnualAllocationEntryComponent_div_127_th_20_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "th", 63)(1, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    const ctx_r6 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵnextContext"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵattribute"]("rowspan", 2);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtextInterpolate1"]("Per ", ctx_r6.levelName, "");
  }
}
function AnnualAllocationEntryComponent_div_127_th_22_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelement"](0, "th", 61);
  }
  if (rf & 2) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("ngStyle", _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵpureFunction0"](1, _c3));
  }
}
function AnnualAllocationEntryComponent_div_127_td_23_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "td", 112);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const i_r31 = ctx.index;
    const ctx_r6 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵnextContext"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtextInterpolate"](ctx_r6.currTotalUnits[i_r31]);
  }
}
function AnnualAllocationEntryComponent_div_127_td_24_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "td", 113);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const ctx_r6 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵnextContext"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtextInterpolate"](ctx_r6.currTotalUnitsFinal);
  }
}
function AnnualAllocationEntryComponent_div_127_th_26_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "th", 114)(1, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](2, "Total Units");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵattribute"]("rowspan", 2);
  }
}
function AnnualAllocationEntryComponent_div_127_th_28_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelement"](0, "th", 61);
  }
  if (rf & 2) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("ngStyle", _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵpureFunction0"](1, _c3));
  }
}
function AnnualAllocationEntryComponent_div_127_td_29_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "td", 112);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const i_r32 = ctx.index;
    const ctx_r6 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵnextContext"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtextInterpolate"](ctx_r6.currTotalCost[i_r32]);
  }
}
function AnnualAllocationEntryComponent_div_127_td_30_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "td", 113);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const ctx_r6 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵnextContext"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtextInterpolate"](ctx_r6.currTotalCostFinal.toFixed(2));
  }
}
function AnnualAllocationEntryComponent_div_127_th_32_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "th", 63)(1, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](2, "Total Cost");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵattribute"]("rowspan", 2);
  }
}
function AnnualAllocationEntryComponent_div_127_th_34_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "th", 75)(1, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    const ctx_r6 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵnextContext"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtextInterpolate1"]("Per ", ctx_r6.levelName, "");
  }
}
function AnnualAllocationEntryComponent_div_127_td_35_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "td", 76);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const row_r33 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtextInterpolate"](row_r33.lastyrperpso);
  }
}
function AnnualAllocationEntryComponent_div_127_td_36_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "td", 115);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const ctx_r6 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵnextContext"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtextInterpolate"](ctx_r6.getTotalPerPso());
  }
}
function AnnualAllocationEntryComponent_div_127_th_38_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "th", 75)(1, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](2, "Total Units");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()();
  }
}
function AnnualAllocationEntryComponent_div_127_td_39_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "td", 76);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const row_r34 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtextInterpolate"](row_r34.lastyrtotunit);
  }
}
function AnnualAllocationEntryComponent_div_127_td_40_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "td", 115);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const ctx_r6 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵnextContext"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtextInterpolate"](ctx_r6.getTotalUnits().toFixed(0));
  }
}
function AnnualAllocationEntryComponent_div_127_th_42_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "th", 75)(1, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](2, "Total Cost");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()();
  }
}
function AnnualAllocationEntryComponent_div_127_td_43_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "td", 76);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const row_r35 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtextInterpolate"](row_r35.lastyrtotcost.toFixed(2));
  }
}
function AnnualAllocationEntryComponent_div_127_td_44_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "td", 115);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const ctx_r6 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵnextContext"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtextInterpolate"](ctx_r6.getTotalPrevCost().toFixed(2));
  }
}
function AnnualAllocationEntryComponent_div_127_th_46_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "th", 75)(1, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](2, "Dispatched");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()();
  }
}
function AnnualAllocationEntryComponent_div_127_td_47_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "td", 76);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const row_r36 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtextInterpolate"](row_r36.dsp_qty);
  }
}
function AnnualAllocationEntryComponent_div_127_td_48_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "td", 115);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const ctx_r6 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵnextContext"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtextInterpolate"](ctx_r6.getTotalPrevDispatched());
  }
}
function AnnualAllocationEntryComponent_div_127_th_50_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "th", 75)(1, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](2, "Cumulative Dispatched");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()();
  }
}
function AnnualAllocationEntryComponent_div_127_td_51_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "td", 76);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const i_r37 = ctx.index;
    const ctx_r6 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵnextContext"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtextInterpolate"](ctx_r6.cummulativeDsp[i_r37]);
  }
}
function AnnualAllocationEntryComponent_div_127_td_52_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelement"](0, "td", 108);
  }
}
function AnnualAllocationEntryComponent_div_127_th_54_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "th", 75)(1, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](2, "Previous Year Figures");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵattribute"]("colspan", 5);
  }
}
function AnnualAllocationEntryComponent_div_127_tr_55_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelement"](0, "tr", 79);
  }
}
function AnnualAllocationEntryComponent_div_127_tr_56_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelement"](0, "tr", 80);
  }
}
function AnnualAllocationEntryComponent_div_127_tr_57_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelement"](0, "tr", 81);
  }
}
function AnnualAllocationEntryComponent_div_127_tr_58_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelement"](0, "tr", 116);
  }
}
function AnnualAllocationEntryComponent_div_127_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "div", 82)(1, "div", 83)(2, "table", 19);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerStart"](3, 84);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](4, AnnualAllocationEntryComponent_div_127_th_4_Template, 1, 2, "th", 21)(5, AnnualAllocationEntryComponent_div_127_td_5_Template, 3, 4, "td", 85)(6, AnnualAllocationEntryComponent_div_127_td_6_Template, 2, 0, "td", 86);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerStart"](7, 87);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](8, AnnualAllocationEntryComponent_div_127_th_8_Template, 3, 1, "th", 24);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerStart"](9, 88);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](10, AnnualAllocationEntryComponent_div_127_th_10_Template, 1, 2, "th", 21)(11, AnnualAllocationEntryComponent_div_127_td_11_Template, 3, 1, "td", 26)(12, AnnualAllocationEntryComponent_div_127_td_12_Template, 1, 0, "td", 86);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerStart"](13, 89);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](14, AnnualAllocationEntryComponent_div_127_th_14_Template, 3, 1, "th", 24);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerStart"](15, 90);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](16, AnnualAllocationEntryComponent_div_127_th_16_Template, 1, 2, "th", 21)(17, AnnualAllocationEntryComponent_div_127_td_17_Template, 2, 4, "td", 91)(18, AnnualAllocationEntryComponent_div_127_td_18_Template, 2, 3, "td", 86);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerStart"](19, 92);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](20, AnnualAllocationEntryComponent_div_127_th_20_Template, 3, 2, "th", 24);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerStart"](21, 93);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](22, AnnualAllocationEntryComponent_div_127_th_22_Template, 1, 2, "th", 21)(23, AnnualAllocationEntryComponent_div_127_td_23_Template, 2, 1, "td", 94)(24, AnnualAllocationEntryComponent_div_127_td_24_Template, 2, 1, "td", 95);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerStart"](25, 96);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](26, AnnualAllocationEntryComponent_div_127_th_26_Template, 3, 1, "th", 97);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerStart"](27, 98);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](28, AnnualAllocationEntryComponent_div_127_th_28_Template, 1, 2, "th", 21)(29, AnnualAllocationEntryComponent_div_127_td_29_Template, 2, 1, "td", 94)(30, AnnualAllocationEntryComponent_div_127_td_30_Template, 2, 1, "td", 95);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerStart"](31, 99);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](32, AnnualAllocationEntryComponent_div_127_th_32_Template, 3, 1, "th", 24);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerStart"](33, 100);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](34, AnnualAllocationEntryComponent_div_127_th_34_Template, 3, 1, "th", 38)(35, AnnualAllocationEntryComponent_div_127_td_35_Template, 2, 1, "td", 39)(36, AnnualAllocationEntryComponent_div_127_td_36_Template, 2, 1, "td", 101);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerStart"](37, 102);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](38, AnnualAllocationEntryComponent_div_127_th_38_Template, 3, 0, "th", 38)(39, AnnualAllocationEntryComponent_div_127_td_39_Template, 2, 1, "td", 39)(40, AnnualAllocationEntryComponent_div_127_td_40_Template, 2, 1, "td", 101);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerStart"](41, 103);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](42, AnnualAllocationEntryComponent_div_127_th_42_Template, 3, 0, "th", 38)(43, AnnualAllocationEntryComponent_div_127_td_43_Template, 2, 1, "td", 39)(44, AnnualAllocationEntryComponent_div_127_td_44_Template, 2, 1, "td", 101);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerStart"](45, 104);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](46, AnnualAllocationEntryComponent_div_127_th_46_Template, 3, 0, "th", 38)(47, AnnualAllocationEntryComponent_div_127_td_47_Template, 2, 1, "td", 39)(48, AnnualAllocationEntryComponent_div_127_td_48_Template, 2, 1, "td", 101);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerStart"](49, 105);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](50, AnnualAllocationEntryComponent_div_127_th_50_Template, 3, 0, "th", 38)(51, AnnualAllocationEntryComponent_div_127_td_51_Template, 2, 1, "td", 39)(52, AnnualAllocationEntryComponent_div_127_td_52_Template, 1, 0, "td", 86);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerStart"](53, 106);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](54, AnnualAllocationEntryComponent_div_127_th_54_Template, 3, 1, "th", 38);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](55, AnnualAllocationEntryComponent_div_127_tr_55_Template, 1, 0, "tr", 51)(56, AnnualAllocationEntryComponent_div_127_tr_56_Template, 1, 0, "tr", 52)(57, AnnualAllocationEntryComponent_div_127_tr_57_Template, 1, 0, "tr", 53)(58, AnnualAllocationEntryComponent_div_127_tr_58_Template, 1, 0, "tr", 107);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()()();
  }
  if (rf & 2) {
    const ctx_r6 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵnextContext"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("dataSource", ctx_r6.previouisYearDetails);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"](53);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("matHeaderRowDef", _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵpureFunction0"](6, _c5));
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("matHeaderRowDef", ctx_r6.ColumnsForCurrAndPrevYear)("matHeaderRowDefSticky", true);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("matRowDefColumns", ctx_r6.ColumnsForCurrAndPrevYear);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("matFooterRowDef", ctx_r6.ColumnsForCurrAndPrevYear);
  }
}
function AnnualAllocationEntryComponent_button_131_Template(rf, ctx) {
  if (rf & 1) {
    const _r38 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵgetCurrentView"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "button", 58);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵlistener"]("click", function AnnualAllocationEntryComponent_button_131_Template_button_click_0_listener() {
      _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵrestoreView"](_r38);
      const ctx_r6 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵnextContext"]();
      return _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵresetView"](ctx_r6.saveAnnualAllocation());
    });
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](1, "Save");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const ctx_r6 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵnextContext"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵclassMapInterpolate1"]("mr-r-10 grad-btn ", ctx_r6.constants.save_btn_class, "");
  }
}
function AnnualAllocationEntryComponent_button_134_Template(rf, ctx) {
  if (rf & 1) {
    const _r39 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵgetCurrentView"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "button", 117);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵlistener"]("click", function AnnualAllocationEntryComponent_button_134_Template_button_click_0_listener() {
      _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵrestoreView"](_r39);
      const ctx_r6 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵnextContext"]();
      return _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵresetView"](ctx_r6.discardAllocation());
    });
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](1, "Discard");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const ctx_r6 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵnextContext"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵclassMapInterpolate1"]("mr-r-10 grad-btn ", ctx_r6.constants.discard_btn_class, "");
  }
}
const _c6 = (a0, a1) => ({
  "is-white": a0,
  "is-green": a1
});
function AnnualDetailComponent_th_10_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelement"](0, "th", 32);
  }
}
function AnnualDetailComponent_td_11_Template(rf, ctx) {
  if (rf & 1) {
    const _r1 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵgetCurrentView"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "td", 33);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵlistener"]("click", function AnnualDetailComponent_td_11_Template_td_click_0_listener() {
      const row_r2 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵrestoreView"](_r1).$implicit;
      const ctx_r2 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵnextContext"]();
      return _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵresetView"](ctx_r2.onClickOfEdit("edit", row_r2.asp_dtl_id));
    });
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](1, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](2, "Edit");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()();
  }
}
function AnnualDetailComponent_th_13_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelement"](0, "th", 32);
  }
}
function AnnualDetailComponent_td_14_Template(rf, ctx) {
  if (rf & 1) {
    const _r4 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵgetCurrentView"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "td", 33);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵlistener"]("click", function AnnualDetailComponent_td_14_Template_td_click_0_listener() {
      const row_r5 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵrestoreView"](_r4).$implicit;
      const ctx_r2 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵnextContext"]();
      return _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵresetView"](ctx_r2.onClickOfEdit("delete", row_r5.asp_dtl_id));
    });
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](1, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](2, "Delete");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()();
  }
}
function AnnualDetailComponent_th_16_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "th", 32)(1, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](2, "Product Name");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()();
  }
}
function AnnualDetailComponent_td_17_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "td", 34);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵpipe"](2, "slice");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const row_r6 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵpropertyInterpolate"]("matTooltip", row_r6.smp_prod_name);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtextInterpolate"](_angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵpipeBind3"](2, 2, row_r6.smp_prod_name, 0, 35));
  }
}
function AnnualDetailComponent_th_19_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "th", 32)(1, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    const ctx_r2 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵnextContext"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtextInterpolate"](ctx_r2.month1);
  }
}
function AnnualDetailComponent_td_20_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "td", 35);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const row_r7 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtextInterpolate"](row_r7.dec);
  }
}
function AnnualDetailComponent_th_22_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "th", 32)(1, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    const ctx_r2 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵnextContext"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtextInterpolate"](ctx_r2.month2);
  }
}
function AnnualDetailComponent_td_23_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "td", 35);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const row_r8 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtextInterpolate"](row_r8.jan);
  }
}
function AnnualDetailComponent_th_25_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "th", 32)(1, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    const ctx_r2 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵnextContext"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtextInterpolate"](ctx_r2.month3);
  }
}
function AnnualDetailComponent_td_26_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "td", 35);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const row_r9 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtextInterpolate"](row_r9.feb);
  }
}
function AnnualDetailComponent_th_28_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "th", 32)(1, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    const ctx_r2 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵnextContext"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtextInterpolate"](ctx_r2.month4);
  }
}
function AnnualDetailComponent_td_29_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "td", 35);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const row_r10 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtextInterpolate"](row_r10.march);
  }
}
function AnnualDetailComponent_th_31_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "th", 32)(1, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    const ctx_r2 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵnextContext"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtextInterpolate"](ctx_r2.month5);
  }
}
function AnnualDetailComponent_td_32_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "td", 35);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const row_r11 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtextInterpolate"](row_r11.april);
  }
}
function AnnualDetailComponent_th_34_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "th", 32)(1, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    const ctx_r2 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵnextContext"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtextInterpolate"](ctx_r2.month6);
  }
}
function AnnualDetailComponent_td_35_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "td", 35);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const row_r12 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtextInterpolate"](row_r12.may);
  }
}
function AnnualDetailComponent_th_37_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "th", 32)(1, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    const ctx_r2 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵnextContext"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtextInterpolate"](ctx_r2.month7);
  }
}
function AnnualDetailComponent_td_38_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "td", 35);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const row_r13 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtextInterpolate"](row_r13.june);
  }
}
function AnnualDetailComponent_th_40_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "th", 32)(1, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    const ctx_r2 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵnextContext"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtextInterpolate"](ctx_r2.month8);
  }
}
function AnnualDetailComponent_td_41_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "td", 35);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const row_r14 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtextInterpolate"](row_r14.july);
  }
}
function AnnualDetailComponent_th_43_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "th", 32)(1, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    const ctx_r2 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵnextContext"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtextInterpolate"](ctx_r2.month9);
  }
}
function AnnualDetailComponent_td_44_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "td", 35);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const row_r15 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtextInterpolate"](row_r15.aug);
  }
}
function AnnualDetailComponent_th_46_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "th", 32)(1, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    const ctx_r2 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵnextContext"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtextInterpolate"](ctx_r2.month10);
  }
}
function AnnualDetailComponent_td_47_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "td", 35);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const row_r16 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtextInterpolate"](row_r16.sept);
  }
}
function AnnualDetailComponent_th_49_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "th", 32)(1, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    const ctx_r2 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵnextContext"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtextInterpolate"](ctx_r2.month11);
  }
}
function AnnualDetailComponent_td_50_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "td", 35);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const row_r17 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtextInterpolate"](row_r17.oct);
  }
}
function AnnualDetailComponent_th_52_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "th", 32)(1, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    const ctx_r2 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵnextContext"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtextInterpolate"](ctx_r2.month12);
  }
}
function AnnualDetailComponent_td_53_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "td", 35);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const row_r18 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtextInterpolate"](row_r18.nov);
  }
}
function AnnualDetailComponent_th_55_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "th", 32)(1, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](2, "COG");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()();
  }
}
function AnnualDetailComponent_td_56_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "td", 36);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const row_r19 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtextInterpolate"](row_r19.cog.toFixed(2));
  }
}
function AnnualDetailComponent_th_58_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "th", 32)(1, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](2, "Total Units");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()();
  }
}
function AnnualDetailComponent_td_59_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "td", 36);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const row_r20 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtextInterpolate"](row_r20.totalUnits.toFixed(0));
  }
}
function AnnualDetailComponent_th_61_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "th", 32)(1, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](2, "Total Cost");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()();
  }
}
function AnnualDetailComponent_td_62_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "td", 36);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const row_r21 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtextInterpolate"](row_r21.totalCost.toFixed(0));
  }
}
function AnnualDetailComponent_tr_63_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelement"](0, "tr", 37);
  }
}
function AnnualDetailComponent_tr_64_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelement"](0, "tr", 38);
  }
  if (rf & 2) {
    const row_r22 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("ngClass", _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵpureFunction2"](1, _c6, row_r22.status === "S", row_r22.status === "A"));
  }
}
function AnnualDetailComponent_div_65_button_1_Template(rf, ctx) {
  if (rf & 1) {
    const _r23 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵgetCurrentView"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "button", 41);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵlistener"]("click", function AnnualDetailComponent_div_65_button_1_Template_button_click_0_listener() {
      _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵrestoreView"](_r23);
      const ctx_r2 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵnextContext"](2);
      return _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵresetView"](ctx_r2.submitforApproval("approval"));
    });
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](1, "Send For Approval");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const ctx_r2 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵnextContext"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵclassMapInterpolate1"]("mr-r-10 grad-btn ", ctx_r2.constants.save_btn_class, "");
  }
}
function AnnualDetailComponent_div_65_button_2_Template(rf, ctx) {
  if (rf & 1) {
    const _r24 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵgetCurrentView"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "button", 41);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵlistener"]("click", function AnnualDetailComponent_div_65_button_2_Template_button_click_0_listener() {
      _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵrestoreView"](_r24);
      const ctx_r2 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵnextContext"](2);
      return _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵresetView"](ctx_r2.submitforApproval("approvalToManager"));
    });
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](1, " Send for Approval to Manager");
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const ctx_r2 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵnextContext"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵclassMapInterpolate1"]("mr-r-10 grad-btn ", ctx_r2.constants.save_btn_class, "");
  }
}
function AnnualDetailComponent_div_65_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "div", 39);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](1, AnnualDetailComponent_div_65_button_1_Template, 2, 3, "button", 40)(2, AnnualDetailComponent_div_65_button_2_Template, 2, 3, "button", 40);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const ctx_r2 = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵnextContext"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("ngIf", ctx_r2.data.managerIndicator);
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("ngIf", ctx_r2.data.isAssistant);
  }
}
const _c7 = ".table-row[_ngcontent-%COMP%]{\n    height: 18px;\n}\n.lightViolet[_ngcontent-%COMP%]{\n\n    background-color:#b9c9fe;;\n}\n\n.lightGreen[_ngcontent-%COMP%]{\n   \n    background-color: #f5f2f0;\n}\nb[_ngcontent-%COMP%]{\n    color: #039;\n}\n\nth.mat-header-cell[_ngcontent-%COMP%] {\n    text-align: center ;\n  }\n.mat-column-warehouse[_ngcontent-%COMP%] {\n    width: 100px;\n}\ninput[_ngcontent-%COMP%]{\n    width: 55px;\n}\nth[_ngcontent-%COMP%]{\n    height: 15px;\n}\n.padding[_ngcontent-%COMP%]{\n    padding-top: 8px;\n    padding-right: 8px;\n    padding-left: 8px;\n}\n.green[_ngcontent-%COMP%]{\n    background-color: #85FA6B  ;\n}\nmat-expansion-panel-header[_ngcontent-%COMP%] {\n    background-color:#b9c9fe;\n}\n  .mat-select-content{\n    width:2000px;\n    background-color: red;\n    font-size: 10px;   \n}\n\n.table-container[_ngcontent-%COMP%] {\n    height: 400px;\n    overflow: auto;\n  }\n\ntr.is-green[_ngcontent-%COMP%] {\n    background:  #85FA6B;\n}\n.mat-column-prevCumulativeDispatch[_ngcontent-%COMP%] {\n    width: 10% !important;\n  }\n\n.mat-column-product[_ngcontent-%COMP%] {\n    width:20% !important;\n  }\n.mat-column-edit[_ngcontent-%COMP%], .mat-column-delete[_ngcontent-%COMP%]{\n    width:5% !important;\n}\n/*# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbIndlYnBhY2s6Ly8uL3NyYy9hcHAvY29tcG9uZW50cy90cmFuc2FjdGlvbi9hbm51YWwtYWxsb2NhdGlvbi1lbnRyeS9hbm51YWwtYWxsb2NhdGlvbi1lbnRyeS5jb21wb25lbnQuY3NzIl0sIm5hbWVzIjpbXSwibWFwcGluZ3MiOiJBQUFBO0lBQ0ksWUFBWTtBQUNoQjtBQUNBOztJQUVJLHdCQUF3QjtBQUM1Qjs7QUFFQTs7SUFFSSx5QkFBeUI7QUFDN0I7QUFDQTtJQUNJLFdBQVc7QUFDZjs7QUFFQTtJQUNJLG1CQUFtQjtFQUNyQjtBQUNGO0lBQ0ksWUFBWTtBQUNoQjtBQUNBO0lBQ0ksV0FBVztBQUNmO0FBQ0E7SUFDSSxZQUFZO0FBQ2hCO0FBQ0E7SUFDSSxnQkFBZ0I7SUFDaEIsa0JBQWtCO0lBQ2xCLGlCQUFpQjtBQUNyQjtBQUNBO0lBQ0ksMkJBQTJCO0FBQy9CO0FBQ0E7SUFDSSx3QkFBd0I7QUFDNUI7QUFDQTtJQUNJLFlBQVk7SUFDWixxQkFBcUI7SUFDckIsZUFBZTtBQUNuQjs7QUFFQTtJQUNJLGFBQWE7SUFDYixjQUFjO0VBQ2hCOztBQUVGO0lBQ0ksb0JBQW9CO0FBQ3hCO0FBQ0E7SUFDSSxxQkFBcUI7RUFDdkI7O0FBRUY7SUFDSSxvQkFBb0I7RUFDdEI7QUFDRjtJQUNJLG1CQUFtQjtBQUN2QiIsInNvdXJjZXNDb250ZW50IjpbIi50YWJsZS1yb3d7XHJcbiAgICBoZWlnaHQ6IDE4cHg7XHJcbn1cclxuLmxpZ2h0VmlvbGV0e1xyXG5cclxuICAgIGJhY2tncm91bmQtY29sb3I6I2I5YzlmZTs7XHJcbn1cclxuXHJcbi5saWdodEdyZWVue1xyXG4gICBcclxuICAgIGJhY2tncm91bmQtY29sb3I6ICNmNWYyZjA7XHJcbn1cclxuYntcclxuICAgIGNvbG9yOiAjMDM5O1xyXG59XHJcblxyXG50aC5tYXQtaGVhZGVyLWNlbGwge1xyXG4gICAgdGV4dC1hbGlnbjogY2VudGVyIDtcclxuICB9XHJcbi5tYXQtY29sdW1uLXdhcmVob3VzZSB7XHJcbiAgICB3aWR0aDogMTAwcHg7XHJcbn1cclxuaW5wdXR7XHJcbiAgICB3aWR0aDogNTVweDtcclxufVxyXG50aHtcclxuICAgIGhlaWdodDogMTVweDtcclxufVxyXG4ucGFkZGluZ3tcclxuICAgIHBhZGRpbmctdG9wOiA4cHg7XHJcbiAgICBwYWRkaW5nLXJpZ2h0OiA4cHg7XHJcbiAgICBwYWRkaW5nLWxlZnQ6IDhweDtcclxufVxyXG4uZ3JlZW57XHJcbiAgICBiYWNrZ3JvdW5kLWNvbG9yOiAjODVGQTZCICA7XHJcbn1cclxubWF0LWV4cGFuc2lvbi1wYW5lbC1oZWFkZXIge1xyXG4gICAgYmFja2dyb3VuZC1jb2xvcjojYjljOWZlO1xyXG59XHJcbjo6bmctZGVlcCAubWF0LXNlbGVjdC1jb250ZW50e1xyXG4gICAgd2lkdGg6MjAwMHB4O1xyXG4gICAgYmFja2dyb3VuZC1jb2xvcjogcmVkO1xyXG4gICAgZm9udC1zaXplOiAxMHB4OyAgIFxyXG59XHJcblxyXG4udGFibGUtY29udGFpbmVyIHtcclxuICAgIGhlaWdodDogNDAwcHg7XHJcbiAgICBvdmVyZmxvdzogYXV0bztcclxuICB9XHJcblxyXG50ci5pcy1ncmVlbiB7XHJcbiAgICBiYWNrZ3JvdW5kOiAgIzg1RkE2QjtcclxufVxyXG4ubWF0LWNvbHVtbi1wcmV2Q3VtdWxhdGl2ZURpc3BhdGNoIHtcclxuICAgIHdpZHRoOiAxMCUgIWltcG9ydGFudDtcclxuICB9XHJcblxyXG4ubWF0LWNvbHVtbi1wcm9kdWN0IHtcclxuICAgIHdpZHRoOjIwJSAhaW1wb3J0YW50O1xyXG4gIH1cclxuLm1hdC1jb2x1bW4tZWRpdCwgLm1hdC1jb2x1bW4tZGVsZXRle1xyXG4gICAgd2lkdGg6NSUgIWltcG9ydGFudDtcclxufSJdLCJzb3VyY2VSb290IjoiIn0= */";
let AnnualAllocationEntryComponent = /*#__PURE__*/(() => {
  class AnnualAllocationEntryComponent {
    fb;
    medicoUtility;
    medicoService;
    allocationService;
    dialog;
    constants = new _classes_constants__WEBPACK_IMPORTED_MODULE_0__.Constants();
    session;
    allocationDate;
    enterFor;
    disableEnterFor = false;
    teams;
    subTeamList;
    brands;
    frequency = new Array();
    copyAllocation = new Array();
    products;
    productList;
    planType;
    levelName;
    excessDeficitPer = 0;
    previouisYearDetailsList;
    previouisYearDetails;
    annualEnteredDetails;
    currPerPso = new Array();
    currTotalUnits = new Array();
    currTotalCost = new Array();
    currPerPsoFinal = 0;
    currTotalUnitsFinal = 0;
    currTotalCostFinal = 0;
    prevDspCount = 0;
    prevActualTeamCount = 0;
    prevDspUnit = 0;
    productCode;
    prevPerPso;
    teamInfo = new Array();
    teamSize;
    productDetails = new Array();
    presentInIndia;
    sampleType;
    cog;
    teamId;
    showTable = false;
    headerFreezed = false;
    disableProdType = false;
    brandId;
    team_ind;
    sub_team_code;
    subTeamFreezed = true;
    data;
    assistantInd;
    managerList = new Array();
    loginId;
    loginEmpId;
    mgrEmpId;
    mgr_id;
    managerDetails = new Array();
    year;
    financialYear;
    alloc_dtl_id = 0;
    asp_id = 0;
    remark;
    allocDocNo;
    allowSave = true;
    msg;
    previousYear;
    temp = 0;
    cummulativeDsp = new Array();
    prevPerPsoQty = new Array();
    //Bimonthly Logic
    biQtr = [[0, 1], [2, 3], [4, 5], [6, 7], [8, 9], [10, 11]];
    //Bimonthly Logic
    qtr = [[0, 1, 2], [3, 4, 5], [6, 7, 8], [9, 10, 11]];
    managerIndicator = true;
    isAssistant = true;
    docType = "XNT";
    //For All Option
    selectedAccessBrand = new Array();
    selectAllAccessBrands = false;
    idsAccessBrand = new Array();
    paginator;
    displayedColumns = ["productCode", "productName", "year", "presenceIndia", "sampleType", "cog", "totalUnits", "totalSize", "actualTeamSize", "perPso", "totalUnits2", "totalSize2", "perPso2", "excess"];
    ColumnsForCurrAndPrevYear = ["currQuarter", "currMonth", "currPerPso", "currTotatUnits", "currTotalCost", "prevPerPso", "prevTotalUnits", "prevTotalCost", "prevDispatched", "prevCumulativeDispatch"];
    reportList;
    form;
    constructor(fb, medicoUtility, medicoService, allocationService, dialog) {
      this.fb = fb;
      this.medicoUtility = medicoUtility;
      this.medicoService = medicoService;
      this.allocationService = allocationService;
      this.dialog = dialog;
      this.form = this.fb.group({
        asp_id: new _angular_forms__WEBPACK_IMPORTED_MODULE_7__.FormControl(""),
        asp_dtl_id: new _angular_forms__WEBPACK_IMPORTED_MODULE_7__.FormControl(""),
        managerDetails: new _angular_forms__WEBPACK_IMPORTED_MODULE_7__.FormControl(""),
        mgr_id: new _angular_forms__WEBPACK_IMPORTED_MODULE_7__.FormControl("", _angular_forms__WEBPACK_IMPORTED_MODULE_7__.Validators.required),
        mgrEmpId: new _angular_forms__WEBPACK_IMPORTED_MODULE_7__.FormControl("", _angular_forms__WEBPACK_IMPORTED_MODULE_7__.Validators.required),
        teamInfo: new _angular_forms__WEBPACK_IMPORTED_MODULE_7__.FormControl("", _angular_forms__WEBPACK_IMPORTED_MODULE_7__.Validators.required),
        teamId: new _angular_forms__WEBPACK_IMPORTED_MODULE_7__.FormControl("", _angular_forms__WEBPACK_IMPORTED_MODULE_7__.Validators.required),
        brandIds: new _angular_forms__WEBPACK_IMPORTED_MODULE_7__.FormControl(""),
        planType: new _angular_forms__WEBPACK_IMPORTED_MODULE_7__.FormControl("", _angular_forms__WEBPACK_IMPORTED_MODULE_7__.Validators.required),
        frequency: new _angular_forms__WEBPACK_IMPORTED_MODULE_7__.FormControl("", _angular_forms__WEBPACK_IMPORTED_MODULE_7__.Validators.required),
        copyAllocationFrom: new _angular_forms__WEBPACK_IMPORTED_MODULE_7__.FormControl(""),
        productId: new _angular_forms__WEBPACK_IMPORTED_MODULE_7__.FormControl("", _angular_forms__WEBPACK_IMPORTED_MODULE_7__.Validators.required),
        teamSize: new _angular_forms__WEBPACK_IMPORTED_MODULE_7__.FormControl(""),
        cog: new _angular_forms__WEBPACK_IMPORTED_MODULE_7__.FormControl(""),
        currPerPso: new _angular_forms__WEBPACK_IMPORTED_MODULE_7__.FormControl(""),
        empId: new _angular_forms__WEBPACK_IMPORTED_MODULE_7__.FormControl(""),
        userId: new _angular_forms__WEBPACK_IMPORTED_MODULE_7__.FormControl(""),
        asp_fin_year: new _angular_forms__WEBPACK_IMPORTED_MODULE_7__.FormControl(""),
        previousYear: new _angular_forms__WEBPACK_IMPORTED_MODULE_7__.FormControl(""),
        brandId: new _angular_forms__WEBPACK_IMPORTED_MODULE_7__.FormControl(""),
        copyPreviousYear: new _angular_forms__WEBPACK_IMPORTED_MODULE_7__.FormControl(""),
        sub_team_code: new _angular_forms__WEBPACK_IMPORTED_MODULE_7__.FormControl("")
      });
      this.medicoService.headingName = "";
      this.session = this.medicoUtility.getSessionVariables();
      this.getTeamBrandsPlans();
      this.frequency = [{
        key: "M",
        value: "Monthly"
      }, {
        key: "B",
        value: "Bi-Monthly"
      }, {
        key: "Q",
        value: "Quarterly"
      }];
      var comp = this.session.company.company.trim();
      if (comp == "SER") {
        this.copyAllocation = [{
          key: "2021",
          value: "Year 2021-2022"
        }, {
          key: "2020",
          value: "Year 2020-2021"
        },
        //added 21-5-2021
        {
          key: "2019",
          value: "Year 2019-2020"
        }, {
          key: "2018",
          value: "Year 2018-2019"
        }, {
          key: "2017",
          value: "Year 2017-2018"
        }];
      }
      if (comp == "PFZ") {
        this.copyAllocation = [{
          key: "2021",
          value: "Year 2020-2021"
        },
        //added 21-5-2021
        {
          key: "2020",
          value: "Year 2019-2020"
        }, {
          key: "2019",
          value: "Year 2018-2019"
        }, {
          key: "2018",
          value: "Year 2017-2018"
        }];
      }
      this.form.get("frequency")?.setValue('M');
      this.form.get("previousYear")?.setValue('2021'); //changed from 2020 to 2021 21-5-2021
      this.msg = "The Allocation is In Draft Mode.";
    }
    ngOnInit() {}
    getTeamBrandsPlans() {
      this.medicoUtility.toggleProgressBar(true);
      this.allocationService.getTeamBrandsPlans(this.session.EMP_ID).subscribe(response => {
        this.allocationDate = response.allocationDate;
        this.teams = response.team;
        this.planType = response.plans;
        this.assistantInd = this.session.LD_EXEC_ASST_IND;
        this.loginId = this.session.LD_ID;
        this.loginEmpId = this.session.EMP_ID;
        //console.log("Login ID "+this.loginId)
        if (this.assistantInd == 'Y') {
          this.managerList = response.managerList;
          this.enterFor = "Enter for";
          this.managerIndicator = false;
        } else {
          this.managerList = [{
            mgr_id: this.loginId,
            mgrEmpId: this.loginEmpId,
            mgr_name: "Self"
          }];
          this.mgrEmpId = this.loginEmpId;
          this.mgr_id = this.loginId;
          this.enterFor = this.session.FNAME + " " + this.session.LNAME; //for UI
          this.disableEnterFor = true;
          this.isAssistant = false;
        }
        this.form.get("asp_id")?.setValue(0);
        this.form.get("asp_dtl_id")?.setValue(0);
        this.year = this.session.CURR_FIN_YEAR;
        this.financialYear = this.session.FINANCIAL_YEAR;
        this.medicoUtility.toggleProgressBar(false);
      });
    }
    setTeam() {
      this.medicoUtility.toggleProgressBar(true);
      if (this.assistantInd == 'Y') {
        this.allocationService.getTeamForManager(this.mgrEmpId).subscribe(response => {
          this.teams = response.team;
          this.allocDocNo = "";
          this.headerFreezed = false;
          this.medicoUtility.toggleProgressBar(false);
        });
        this.resetTable();
      }
    }
    checkSubTeamExist() {
      this.teamInfo = this.form.get("teamInfo")?.value;
      this.teamId = this.teamInfo[0];
      this.team_ind = this.teamInfo[3];
      if (this.team_ind == 'Y') {
        this.subTeamFreezed = false;
        this.allocationService.getSubTeamList(this.teamId).subscribe(response => {
          this.subTeamList = response.subTeamList;
        });
      } else {
        this.form.get("sub_team_code")?.setValue(0);
        this.subTeamFreezed = true;
        this.resetBrand();
      }
    }
    resetBrand() {
      this.medicoUtility.toggleProgressBar(true);
      this.teamInfo = this.form.get("teamInfo")?.value;
      this.teamId = this.teamInfo[0];
      this.team_ind = this.teamInfo[4];
      this.productList = new _angular_material_table__WEBPACK_IMPORTED_MODULE_8__.MatTableDataSource();
      this.disableProdType = false;
      this.allocationService.checkedTeamExist(this.mgrEmpId, this.mgr_id, this.teamId, this.year, this.form.get("sub_team_code")?.value).subscribe(response => {
        this.prevActualTeamCount = response.privious_actual_team_size;
        if (response.isExist == true) {
          this.form.get("asp_id")?.setValue(response.aspHeader.asp_id);
          this.allocDocNo = response.aspHeader.asp_alloc_number;
          if (response.aspHeader.asp_appr_status == 'F') {
            this.allowSave = false;
            this.disableProdType = true;
            this.msg = "The following Allocation is in approval after Approval you can Edit/Delete";
          } else if (response.aspHeader.asp_appr_status == 'E') {
            this.allowSave = true;
            this.disableProdType = false;
            this.msg = "The Allocation is In Draft Mode.";
          } else if (response.aspHeader.asp_appr_status == 'A') {
            this.allowSave = true;
            this.disableProdType = false;
            this.msg = "The Allocation is Approved.If any modifation done you have to send it for approval";
          }
          this.getAnnualPlanEnteredProductDetail();
        } else {
          this.allocDocNo = "";
          this.allowSave = true;
        }
        this.medicoUtility.toggleProgressBar(false);
      });
      this.form.get("brandIds")?.reset();
      this.form.get("planType")?.reset();
    }
    setEmpDetails() {
      this.managerDetails = this.form.get("managerDetails")?.value;
      this.mgr_id = this.managerDetails[0];
      this.mgrEmpId = this.managerDetails[1];
      this.setTeam();
    }
    setValuesForProduct(num) {
      //console.log(num);
    }
    getTotalPrevDispatched() {
      return this.previouisYearDetailsList.map(t => t.dsp_qty).reduce((acc, value) => acc + value, 0);
    }
    getTotalPerPso() {
      return this.previouisYearDetailsList.map(t => t.lastyrperpso).reduce((acc, value) => acc + value, 0);
    }
    getTotalPrevCost() {
      return this.previouisYearDetailsList.map(t => t.lastyrtotcost).reduce((acc, value) => acc + value, 0);
    }
    getTotalUnits() {
      return this.previouisYearDetailsList.map(t => t.lastyrtotunit).reduce((acc, value) => acc + value, 0);
    }
    claculateFields($event, i) {
      //console.log("i"+i+"jj"+($event.target.value.trim ));
      var num = "0";
      if ($event.target.value.trim != '') {
        num = $event.target.value;
        //console.log("value IN"+$event.target.value);
      }
      if (i == 0 && this.form.get("frequency")?.value == 'M') {
        //console.log(this.currPerPso.length);
        for (i = 0; i < this.currPerPso.length; i++) {
          this.currPerPso[i] = num;
          this.currTotalUnits[i] = parseFloat(num) * this.teamSize;
          this.currTotalCost[i] = (this.currTotalUnits[i] * this.cog).toFixed(2);
          var tempTotal = 0;
          for (let i = 0; i < this.currPerPso.length; i++) {
            this.currPerPsoFinal = tempTotal + parseFloat(this.currPerPso[i]);
            //console.log( this.currPerPsoFinal);
            tempTotal = this.currPerPsoFinal;
          }
          tempTotal = 0;
          for (let i = 0; i < this.currTotalUnits.length; i++) {
            this.currTotalUnitsFinal = tempTotal + parseFloat(this.currTotalUnits[i]);
            tempTotal = this.currTotalUnitsFinal;
          }
          tempTotal = 0;
          for (let i = 0; i < this.currTotalCost.length; i++) {
            this.currTotalCostFinal = tempTotal + parseFloat(this.currTotalCost[i]);
            tempTotal = this.currTotalCostFinal;
          }
        }
      } else if (this.form.get("frequency")?.value == 'Q') {
        var k = 0;
        for (let l = 0; l < this.qtr.length; l++) {
          if (this.qtr[l].includes(i)) {
            for (let j = 0; j < this.qtr[l].length; j++) {
              k = this.qtr[l][j];
              if (k == i) {
                this.currTotalUnits[i] = parseFloat(num) * this.teamSize;
                this.currTotalCost[i] = (this.currTotalUnits[i] * this.cog).toFixed(2);
              } else {
                this.currPerPso[k] = 0;
                this.currTotalUnits[k] = parseFloat(this.currPerPso[k]) * this.teamSize;
                this.currTotalCost[k] = (this.currTotalUnits[k] * this.cog).toFixed(2);
              }
            }
          }
        }
        var tempTotal = 0;
        for (let i = 0; i < this.currPerPso.length; i++) {
          this.currPerPsoFinal = tempTotal + parseFloat(this.currPerPso[i]);
          tempTotal = this.currPerPsoFinal;
        }
        tempTotal = 0;
        for (let i = 0; i < this.currTotalUnits.length; i++) {
          this.currTotalUnitsFinal = tempTotal + parseFloat(this.currTotalUnits[i]);
          tempTotal = this.currTotalUnitsFinal;
        }
        tempTotal = 0;
        for (let i = 0; i < this.currTotalCost.length; i++) {
          this.currTotalCostFinal = tempTotal + parseFloat(this.currTotalCost[i]);
          tempTotal = this.currTotalCostFinal;
        }
      } else if (this.form.get("frequency")?.value == 'B') {
        var k = 0;
        for (let l = 0; l < this.biQtr.length; l++) {
          if (this.biQtr[l].includes(i)) {
            for (let j = 0; j < this.biQtr[l].length; j++) {
              k = this.biQtr[l][j];
              if (k == i) {
                this.currTotalUnits[i] = parseFloat(num) * this.teamSize;
                this.currTotalCost[i] = (this.currTotalUnits[i] * this.cog).toFixed(2);
              } else {
                this.currPerPso[k] = 0;
                this.currTotalUnits[k] = parseFloat(this.currPerPso[k]) * this.teamSize;
                this.currTotalCost[k] = (this.currTotalUnits[k] * this.cog).toFixed(2);
              }
            }
          }
        }
        var tempTotal = 0;
        for (let i = 0; i < this.currPerPso.length; i++) {
          this.currPerPsoFinal = tempTotal + parseFloat(this.currPerPso[i]);
          tempTotal = this.currPerPsoFinal;
        }
        tempTotal = 0;
        for (let i = 0; i < this.currTotalUnits.length; i++) {
          this.currTotalUnitsFinal = tempTotal + parseFloat(this.currTotalUnits[i]);
          tempTotal = this.currTotalUnitsFinal;
        }
        tempTotal = 0;
        for (let i = 0; i < this.currTotalCost.length; i++) {
          this.currTotalCostFinal = tempTotal + parseFloat(this.currTotalCost[i]);
          tempTotal = this.currTotalCostFinal;
        }
      } else {
        this.currTotalUnits[i] = parseFloat(num) * this.teamSize;
        this.currTotalCost[i] = (this.currTotalUnits[i] * this.cog).toFixed(2);
        var tempTotal = 0;
        for (let i = 0; i < this.currPerPso.length; i++) {
          this.currPerPsoFinal = tempTotal + parseFloat(this.currPerPso[i]);
          //console.log( this.currPerPsoFinal);
          tempTotal = this.currPerPsoFinal;
        }
        tempTotal = 0;
        for (let i = 0; i < this.currTotalUnits.length; i++) {
          this.currTotalUnitsFinal = tempTotal + parseFloat(this.currTotalUnits[i]);
          tempTotal = this.currTotalUnitsFinal;
        }
        tempTotal = 0;
        for (let i = 0; i < this.currTotalCost.length; i++) {
          this.currTotalCostFinal = tempTotal + parseFloat(this.currTotalCost[i]);
          tempTotal = this.currTotalCostFinal;
        }
      }
      this.calculateExcess();
    }
    setBrands() {
      this.medicoUtility.toggleProgressBar(true);
      this.form.get("brandIds")?.reset();
      this.teamInfo = this.form.get("teamInfo")?.value;
      this.teamSize = this.teamInfo[1];
      this.allocationService.getTeamBrands(this.mgrEmpId, this.teamInfo[0], this.form.get("planType")?.value, this.form.get("sub_team_code")?.value).subscribe(response => {
        this.brands = response.brands;
        this.levelName = this.teamInfo[2];
        this.idsAccessBrand = response.brands.map(x => x.smp_sa_prod_group);
        this.medicoUtility.toggleProgressBar(false);
      });
      this.resetTable();
    }
    setProductList() {
      this.teamInfo = this.form.get("teamInfo")?.value;
      this.setBrand();
      if (this.form.get("brandIds")?.valid && this.form.get("planType")?.valid) {
        this.medicoUtility.toggleProgressBar(true);
        this.allocationService.setProductList(this.teamInfo[0], this.form.get("brandIds")?.value, this.form.get("planType")?.value, this.allocDocNo).subscribe(response => {
          this.products = response.productList;
          this.productList = new _angular_material_table__WEBPACK_IMPORTED_MODULE_8__.MatTableDataSource(response.productList.slice(0));
          this.medicoUtility.toggleProgressBar(false);
        });
      }
      this.resetTable();
    }
    setPreviousYearDetails() {
      this.medicoUtility.toggleProgressBar(true);
      this.currPerPsoFinal = 0;
      this.currTotalUnitsFinal = 0;
      this.currTotalCostFinal = 0;
      this.teamInfo = this.form.get("teamInfo")?.value;
      this.teamId = this.teamInfo[0];
      this.previousYear = this.form.get("previousYear")?.value;
      for (let i = 0; i < 12; i++) {
        this.currPerPso[i] = 0;
        this.currTotalCost[i] = 0;
        this.currTotalUnits[i] = 0;
      }
      this.allocationService.setPreviousYearDetails(this.form.get("productId")?.value, this.teamInfo[0], this.form.get("brandIds")?.value, this.form.get("planType")?.value, this.previousYear).subscribe(response => {
        if (response.producExist == true) {
          this.medicoService.popup("Annual Sample Plan", response.RESPONSE_MESSAGE);
          this.resetTable();
        } else {
          this.previouisYearDetailsList = response.previouisYearDetails.slice(0);
          this.previouisYearDetails = new _angular_material_table__WEBPACK_IMPORTED_MODULE_8__.MatTableDataSource(this.previouisYearDetailsList);
          this.presentInIndia = "< " + response.presentInIndia + " years";
          this.sampleType = response.supplyType;
          this.cog = response.cog.toFixed(2);
          ;
          this.brandId = response.brandId;
          this.productCode = response.smp_erp_prod_cd;
          this.prevDspUnit = response.prvyrdsp_unit;
          this.prevDspCount = response.prvyrdspcnt;
          this.prevPerPso = response.prvdsp_perpso;
          this.setCumulativeDispatch();
          this.showTable = true;
        }
        this.medicoUtility.toggleProgressBar(false);
      });
    }
    setCumulativeDispatch() {
      this.temp = 0;
      this.cummulativeDsp = new Array(); //added 21-5-2021
      for (let i = 0; i < 12; i++) {
        if (this.previouisYearDetailsList != undefined && this.previouisYearDetailsList != null) {
          if (this.temp == this.prevDspUnit) {
            return 0;
          } else {
            this.cummulativeDsp[i] = this.temp + this.previouisYearDetailsList[i].dsp_qty;
            this.temp = this.cummulativeDsp[i];
          }
        } else {
          //added 21-5-2021
          this.cummulativeDsp[i] = 0;
        }
      }
    }
    copyPreviousYearDetails() {
      for (let i = 0; i < 11; i++) {
        this.currPerPso[i] = 0;
        this.currTotalCost[i] = 0;
        this.currTotalUnits[i] = 0;
      }
    }
    calculateExcess() {
      if (this.prevDspUnit != 0 && this.prevDspUnit != undefined) {
        this.excessDeficitPer = parseFloat(((Number(this.currTotalUnitsFinal) - this.prevDspUnit) * 100 / this.prevDspUnit).toFixed(2));
      } else {
        this.excessDeficitPer = 0;
      }
    }
    getRowSpan(index) {
      if (index % 3 == 0) {
        return 3;
      } else {
        return 0;
      }
    }
    saveAnnualAllocation() {
      this.form.get("teamSize")?.setValue(Number(this.teamSize));
      this.form.get("cog")?.setValue(Number(this.cog));
      this.form.get("currPerPso")?.setValue(this.currPerPso);
      this.form.get("teamId")?.setValue(this.teamId);
      this.form.get("empId")?.setValue(this.session.EMP_ID);
      this.form.get("userId")?.setValue(this.session.LD_ID);
      this.form.get("mgrEmpId")?.setValue(this.mgrEmpId);
      this.form.get("mgr_id")?.setValue(this.mgr_id);
      this.form.get("asp_fin_year")?.setValue(this.year);
      this.form.get("asp_dtl_id")?.setValue(this.alloc_dtl_id);
      this.form.get("brandId")?.setValue(this.brandId);
      if (this.allowSave == true) {
        this.medicoUtility.toggleProgressBar(true);
        if (this.form.valid) {
          this.allocationService.saveAnnualAllocation(this.form).subscribe(response => {
            if (response.DATA_SAVED) {
              this.alloc_dtl_id = 0;
              this.medicoService.popup("Annual Sample Plan", response.RESPONSE_MESSAGE);
              if (response.aspHeader != undefined && response.aspHeader != null) {
                //for update its getting null
                this.allocDocNo = response.aspHeader.asp_alloc_number;
                this.form.get("asp_id")?.setValue(response.aspHeader.asp_id);
              }
              this.headerFreezed = true;
              this.disableEnterFor = true;
              this.resetTable();
              this.setProductList();
              this.medicoUtility.toggleProgressBar(false);
            } else {
              this.medicoService.popup("Annual Sample Plan", response.RESPONSE_MESSAGE);
              this.medicoUtility.toggleProgressBar(false);
            }
          });
        } else {
          this.medicoUtility.validateAllFields(this.form);
          this.medicoUtility.toggleProgressBar(false);
        }
      } else {
        this.medicoService.confirmBox("Message", "The following Allocation is in Approval. After Approval You can Modify.", "Ok", "");
      }
    }
    deleteProduct(alloc_dtl_id) {
      this.allocationService.deleteProductAnnualPlan(alloc_dtl_id).subscribe(response => {
        this.medicoService.popup("Annual Sample Plan", response.RESPONSE_MESSAGE);
        this.alloc_dtl_id = 0;
      });
    }
    getAnnualPlanEnteredProductDetail() {
      this.medicoUtility.toggleProgressBar(true);
      this.allocationService.getAnnualPlanEnteredProductDetail(this.teamInfo[0], "0", this.year, this.mgr_id) //0 set to get all brands details
      .subscribe(response => {
        if (response.annualEnteredData != null && response.annualEnteredData != undefined) {
          this.annualEnteredDetails = new _angular_material_table__WEBPACK_IMPORTED_MODULE_8__.MatTableDataSource(response.annualEnteredData.slice(0));
          this.medicoUtility.toggleProgressBar(false);
          const dialogRef = this.dialog.open(AnnualDetailComponent, {
            width: '1650px',
            height: '650px',
            data: {
              list: this.annualEnteredDetails,
              form: this.form,
              allowSave: this.allowSave,
              msg: this.msg,
              isAssistant: this.isAssistant,
              managerIndicator: this.managerIndicator
            }
          });
          dialogRef.afterClosed().subscribe(result => {
            if (result.action == 'approval') {
              this.allowSave = false;
              this.submitforApproval();
              return;
            }
            if (result.action == 'approvalToManager') {
              this.allowSave = false;
              this.sendApprovalToManager();
              return;
            }
            if (result.detailId != undefined || result.detailId != null) {
              this.alloc_dtl_id = result.detailId;
              if (result.action == 'delete') {
                if (this.allowSave != true) {
                  this.medicoService.confirmBox("Message", this.msg, "Ok", "");
                  return;
                }
                this.deleteProduct(this.alloc_dtl_id);
                return;
              }
              this.medicoUtility.toggleProgressBar(true);
              this.allocationService.getDataToModifyProduct(this.alloc_dtl_id, this.mgrEmpId).subscribe(response => {
                for (let i = 0; i < 12; i++) {
                  this.currPerPso[i] = 0;
                  this.currTotalCost[i] = 0;
                  this.currTotalUnits[i] = 0;
                }
                this.teamInfo = this.form.get("teamInfo")?.value;
                this.teamSize = this.teamInfo[1];
                this.levelName = this.teamInfo[2];
                this.products = response.productList;
                this.productList = new _angular_material_table__WEBPACK_IMPORTED_MODULE_8__.MatTableDataSource(response.productList.slice(0));
                this.previouisYearDetailsList = response.previouisYearDetails.slice(0);
                this.previouisYearDetails = new _angular_material_table__WEBPACK_IMPORTED_MODULE_8__.MatTableDataSource(this.previouisYearDetailsList);
                this.brands = response.brands;
                this.presentInIndia = "< " + response.presentInIndia + " years";
                this.sampleType = response.supplyType;
                this.cog = response.cog;
                this.productCode = response.smp_erp_prod_cd;
                this.prevDspUnit = response.prvyrdsp_unit;
                this.prevDspCount = response.prvyrdspcnt;
                this.prevPerPso = response.prvdsp_perpso;
                this.form.get("productId")?.setValue(response.aspDetail.asp_prod_id);
                this.showTable = true;
                this.currPerPso[0] = response.aspDetail.asp_prdqty01;
                this.currPerPso[1] = response.aspDetail.asp_prdqty02;
                this.currPerPso[2] = response.aspDetail.asp_prdqty03;
                this.currPerPso[3] = response.aspDetail.asp_prdqty04;
                this.currPerPso[4] = response.aspDetail.asp_prdqty05;
                this.currPerPso[5] = response.aspDetail.asp_prdqty06;
                this.currPerPso[6] = response.aspDetail.asp_prdqty07;
                this.currPerPso[7] = response.aspDetail.asp_prdqty08;
                this.currPerPso[8] = response.aspDetail.asp_prdqty09;
                this.currPerPso[9] = response.aspDetail.asp_prdqty10;
                this.currPerPso[10] = response.aspDetail.asp_prdqty11;
                this.currPerPso[11] = response.aspDetail.asp_prdqty12;
                this.form.get("frequency")?.setValue(response.aspDetail.asp_frequency);
                this.form.get("asp_id")?.setValue(response.aspDetail.asp_id);
                this.brandId = response.brandId;
                this.form.get("planType")?.setValue(response.aspDetail.asp_prod_type);
                this.headerFreezed = true;
                this.calculateFieldForModify();
                this.setCumulativeDispatch();
                this.medicoUtility.toggleProgressBar(false);
              });
            }
          });
        } else {
          this.medicoService.confirmBox("Message", "No Saved Product found for selected Team.", "Ok", "");
          this.medicoUtility.toggleProgressBar(false);
        }
      });
    }
    calculateFieldForModify() {
      for (let i = 0; i < this.currPerPso.length; i++) {
        var tempTotal = 0;
        this.currTotalUnits[i] = this.currPerPso[i] * this.teamSize;
        this.currTotalCost[i] = (this.currTotalUnits[i] * this.cog).toFixed(2);
        for (let i = 0; i < this.currPerPso.length; i++) {
          this.currPerPsoFinal = tempTotal + parseFloat(this.currPerPso[i]);
          //console.log( this.currPerPsoFinal);
          tempTotal = this.currPerPsoFinal;
        }
        tempTotal = 0;
        for (let i = 0; i < this.currTotalUnits.length; i++) {
          this.currTotalUnitsFinal = tempTotal + parseFloat(this.currTotalUnits[i]);
          tempTotal = this.currTotalUnitsFinal;
        }
        tempTotal = 0;
        for (let i = 0; i < this.currTotalCost.length; i++) {
          this.currTotalCostFinal = tempTotal + parseFloat(this.currTotalCost[i]);
          tempTotal = this.currTotalCostFinal;
        }
      }
      this.calculateExcess();
    }
    submitforApproval() {
      this.remark = "";
      this.asp_id = this.form.get("asp_id")?.value;
      this.medicoUtility.toggleProgressBar(true);
      ////console.log("remark for grn is "+this.Remark);
      if (this.asp_id != 0 && this.asp_id != undefined) {
        this.allocationService.AnnualAllocationSelfApproval(this.asp_id, this.mgrEmpId, this.mgrEmpId, this.remark).subscribe(result => {
          if (result.DATA_SAVED == true) {
            this.medicoService.popup("Annual Sample Plan", "Forwarded For Approval");
            this.form.get("teamInfo")?.reset();
            this.teamInfo = new Array();
            this.resetTable();
          } else {
            this.medicoService.popup("Annual Sample Plan", result.RESPONSE_MESSAGE);
          }
          this.medicoUtility.toggleProgressBar(false);
        });
      } else {
        this.medicoService.confirmBox("Message", "Please Save!", "Ok", "");
        this.medicoUtility.toggleProgressBar(false);
      }
    }
    discardAllocation() {
      if (this.alloc_dtl_id != 0 && this.alloc_dtl_id != undefined) {
        this.deleteProduct(this.alloc_dtl_id);
        this.resetTable();
      } else {
        this.medicoService.confirmBox("Message", "Please Save Product First", "Ok", "");
        ;
      }
    }
    onNewAllocation() {
      this.form.get("teamInfo")?.reset();
      this.form.get("managerDetails")?.reset();
      this.allocDocNo = "";
      this.headerFreezed = false;
      this.disableEnterFor = false;
      this.resetTable();
    }
    copyFromPreviousYear() {
      for (let i = 0; i < this.currPerPso.length; i++) {
        if (this.form.get("copyPreviousYear")?.value == false && this.setPreviousYearDetails != null) {
          this.currPerPso[i] = this.previouisYearDetailsList[i].lastyrperpso;
        } else {
          this.currPerPso[i] = 0;
          this.excessDeficitPer = 0;
        }
        this.currTotalUnits[i] = parseFloat(this.currPerPso[i]) * this.teamSize;
        this.currTotalCost[i] = (this.currTotalUnits[i] * this.cog).toFixed(2);
        var tempTotal = 0;
        for (let i = 0; i < this.currPerPso.length; i++) {
          this.currPerPsoFinal = tempTotal + parseFloat(this.currPerPso[i]);
          tempTotal = this.currPerPsoFinal;
        }
        tempTotal = 0;
        for (let i = 0; i < this.currTotalUnits.length; i++) {
          this.currTotalUnitsFinal = tempTotal + parseFloat(this.currTotalUnits[i]);
          tempTotal = this.currTotalUnitsFinal;
        }
        tempTotal = 0;
        for (let i = 0; i < this.currTotalCost.length; i++) {
          this.currTotalCostFinal = tempTotal + parseFloat(this.currTotalCost[i]);
          tempTotal = this.currTotalCostFinal;
        }
      }
      this.calculateExcess();
    }
    resetTable() {
      this.currPerPsoFinal = 0;
      this.currTotalUnitsFinal = 0;
      this.currTotalCostFinal = 0;
      this.prevDspCount = 0;
      this.prevDspUnit = 0;
      this.productCode = "";
      this.prevPerPso = "";
      this.presentInIndia = "";
      this.cog = 0;
      this.showTable = false;
      this.sampleType = "";
      this.allowSave = true;
      this.form.get("productId")?.reset();
      this.currPerPsoFinal = 0;
      this.currTotalUnitsFinal = 0;
      this.currTotalCostFinal = 0;
      this.excessDeficitPer = 0;
      this.sub_team_code = "";
    }
    sendApprovalToManager() {
      this.asp_id = this.form.get("asp_id")?.value;
      this.medicoUtility.toggleProgressBar(true);
      this.allocationService.sendApprovalToManager(this.asp_id, this.year, this.session.EMP_ID, this.mgrEmpId, "annualPlan").subscribe(response => {
        if (response.DATA_SAVED == true) {
          this.medicoService.popup("Annual Sample Plan", response.RESPONSE_MESSAGE);
          this.form.get("teamInfo")?.reset();
          this.resetTable();
          this.medicoUtility.toggleProgressBar(false);
        } else {
          this.medicoService.popup("Error", response.RESPONSE_MESSAGE);
          this.medicoUtility.toggleProgressBar(false);
        }
      });
    }
    setBrand() {
      this.selectedAccessBrand = this.form.get("brandIds")?.value;
      if (this.selectedAccessBrand[this.selectedAccessBrand.length - 1] == 0) {
        this.selectAllAccessBrands = true;
        this.idsAccessBrand.push(0);
        this.selectedAccessBrand = this.idsAccessBrand;
        this.form.get("brandIds")?.setValue(this.selectedAccessBrand);
      } else if (this.selectAllAccessBrands == true && !this.selectedAccessBrand.includes(0)) {
        this.selectAllAccessBrands = false;
        this.selectedAccessBrand = new Array();
        this.form.get("brandIds")?.setValue(this.selectedAccessBrand);
      }
      if (this.selectAllAccessBrands == false && this.selectedAccessBrand.includes(0)) {
        this.idsAccessBrand.push("0");
        this.selectAllAccessBrands = true;
        this.selectedAccessBrand = new Array();
        this.selectedAccessBrand = this.idsAccessBrand;
        this.form.controls["brandIds"].setValue(this.selectedAccessBrand);
        // //console.log("if :: "+this.form.get("teaminfo").value);
      }
    }
    static ɵfac = function AnnualAllocationEntryComponent_Factory(__ngFactoryType__) {
      return new (__ngFactoryType__ || AnnualAllocationEntryComponent)(_angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵdirectiveInject"](_angular_forms__WEBPACK_IMPORTED_MODULE_7__.FormBuilder), _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵdirectiveInject"](_classes_medico_utility__WEBPACK_IMPORTED_MODULE_1__.MedicoUtility), _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵdirectiveInject"](_services_medico_medico_service__WEBPACK_IMPORTED_MODULE_2__.MedicoService), _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵdirectiveInject"](src_app_services_allocation_allocation_service__WEBPACK_IMPORTED_MODULE_3__.AllocationService), _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵdirectiveInject"](_angular_material_dialog__WEBPACK_IMPORTED_MODULE_9__.MatDialog));
    };
    static ɵcmp = /*@__PURE__*/_angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵdefineComponent"]({
      type: AnnualAllocationEntryComponent,
      selectors: [["app-annual-allocation-entry"]],
      viewQuery: function AnnualAllocationEntryComponent_Query(rf, ctx) {
        if (rf & 1) {
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵviewQuery"](_angular_material_paginator__WEBPACK_IMPORTED_MODULE_10__.MatPaginator, 5);
        }
        if (rf & 2) {
          let _t;
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵqueryRefresh"](_t = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵloadQuery"]()) && (ctx.paginator = _t.first);
        }
      },
      decls: 137,
      vars: 44,
      consts: [[3, "formGroup"], [1, "pl-1", "pr-1", "pb-1", "container"], ["hideToggle", "", 3, "expanded"], ["fxLayout", "row", "fxLayout.lt-md", "column", 1, "mt-1"], ["fxFlex", "100%"], ["fxLayout", "row", "fxLayout.lt-md", "column", 1, "padding"], ["fxFlex", "20%"], ["fxLayout", "row", "fxLayout.lt-md", "column"], ["fxFlex", "14%", "appearance", "outline", 2, "margin-bottom", "-10px", 3, "ngClass"], ["formControlName", "managerDetails", 3, "selectionChange", "disabled"], [3, "value", 4, "ngFor", "ngForOf"], ["formControlName", "teamInfo", 3, "selectionChange", "disabled"], ["fxFlex", "13%", "appearance", "outline", 2, "margin-bottom", "-10px", 3, "ngClass"], ["formControlName", "sub_team_code", 3, "selectionChange", "disabled"], ["formControlName", "planType", 3, "selectionChange", "disabled"], ["formControlName", "brandIds", "multiple", "", 3, "selectionChange"], [3, "value"], ["formControlName", "frequency"], [1, "mat-elevation-z2", "width-only-100"], ["mat-table", "", "matSort", "", 1, "table-borders-right", "width-only-100", "thin-th", "mat-td-p", "mat-th-p", 3, "dataSource"], ["matColumnDef", "productCode"], ["mat-header-cell", "", "class", "lightViolet", 3, "ngStyle", 4, "matHeaderCellDef"], ["mat-cell", "", "matInput", "", 3, "matTooltip", 4, "matCellDef"], ["matColumnDef", "productCode_"], ["mat-header-cell", "", "class", "lightViolet", 4, "matHeaderCellDef"], ["matColumnDef", "productName"], ["mat-cell", "", 4, "matCellDef"], ["matColumnDef", "productName_"], ["matColumnDef", "year"], ["matColumnDef", "year_"], ["matColumnDef", "presenceIndia"], ["mat-cell", "", 4, "matCellDef", "matCellDefIndex"], ["matColumnDef", "presenceIndia_"], ["matColumnDef", "sampleType"], ["matColumnDef", "sampleType_"], ["matColumnDef", "cog"], ["matColumnDef", "cog_"], ["matColumnDef", "totalUnits"], ["mat-header-cell", "", "class", "lightGreen", 4, "matHeaderCellDef"], ["mat-cell", "", "class", "text-right", 4, "matCellDef"], ["matColumnDef", "totalSize"], ["matColumnDef", "actualTeamSize"], ["matColumnDef", "perPso"], ["matColumnDef", "totalUnits2"], ["matColumnDef", "totalSize2"], ["matColumnDef", "perPso2"], ["matColumnDef", "excess"], ["mat-cell", "", "class", "text-center", 4, "matCellDef"], ["matColumnDef", "excess_"], ["matColumnDef", "previousYearDispatch"], ["matColumnDef", "currentYearPlan"], ["mat-header-row", "", "class", "table-row", 4, "matHeaderRowDef"], ["mat-header-row", "", "class", "table-row", "mat-sort-header", "", 4, "matHeaderRowDef", "matHeaderRowDefSticky"], ["mat-row", "", "class", "table-row", 4, "matRowDef", "matRowDefColumns"], ["fxLayout", "row", "fxLayout.lt-md", "column", "class", "p-1", 4, "ngIf"], ["fxLayout", "row", 1, "mt-1", "mb-1"], ["type", "button", "matTooltip", "Choose a New Manager/Team", 3, "click"], ["type", "button", 3, "class", "click", 4, "ngIf"], ["type", "button", 3, "click"], ["type", "button", "matTooltip", "Click to Discard Current Saved product", 3, "class", "click", 4, "ngIf"], ["type", "button", "routerLink", "/medico-user/home"], ["mat-header-cell", "", 1, "lightViolet", 3, "ngStyle"], ["mat-cell", "", "matInput", "", 3, "matTooltip"], ["mat-header-cell", "", 1, "lightViolet"], ["mat-cell", ""], [2, "margin-bottom", "-10px"], ["formControlName", "productId", 3, "selectionChange"], ["ngSize", "30", 3, "value", "matTooltip", 4, "ngFor", "ngForOf"], ["ngSize", "30", 3, "value", "matTooltip"], ["fxFlex", "70%", 2, "margin-bottom", "-10px"], ["formControlName", "previousYear", 3, "selectionChange"], ["type", "checkbox", "formControlName", "copyPreviousYear", "value", "true", 2, "margin-top", "20px", 3, "click"], ["matInput", "", "readonly", "", 3, "ngModelChange", "change", "ngModel", "ngModelOptions"], ["matInput", "", "readOnly", "", 3, "ngModelChange", "change", "ngModel", "ngModelOptions"], ["appNumbersOnly", "", "matInput", "", "readOnly", "", 1, "text-center", 3, "ngModelChange", "change", "ngModel", "ngModelOptions"], ["mat-header-cell", "", 1, "lightGreen"], ["mat-cell", "", 1, "text-right"], ["appNumbersOnly", "", "step", "0.01", "matInput", "", "readOnly", "", 1, "text-center", 3, "ngModelChange", "ngModel", "ngModelOptions"], ["mat-cell", "", 1, "text-center"], ["mat-header-row", "", 1, "table-row"], ["mat-header-row", "", "mat-sort-header", "", 1, "table-row"], ["mat-row", "", 1, "table-row"], ["fxLayout", "row", "fxLayout.lt-md", "column", 1, "p-1"], [1, "table-container1", "mat-elevation-z2", "width-only-100"], ["matColumnDef", "currQuarter"], ["mat-cell", "", 3, "display", 4, "matCellDef"], ["mat-footer-cell", "", 4, "matFooterCellDef"], ["matColumnDef", "currQuarter_"], ["matColumnDef", "currMonth"], ["matColumnDef", "currMonth_"], ["matColumnDef", "currPerPso"], ["mat-cell", "", "class", "green", 4, "matCellDef"], ["matColumnDef", "currPerPso_"], ["matColumnDef", "currTotatUnits"], ["step", "0.01", "mat-cell", "", "class", "text-right", 4, "matCellDef"], ["step", "0.01", "mat-footer-cell", "", "class", "text-right", 4, "matFooterCellDef"], ["matColumnDef", "currTotatUnits_"], ["mat-header-cell", "", "class", "lightViolet", "readonly", "", 4, "matHeaderCellDef"], ["matColumnDef", "currTotalCost"], ["matColumnDef", "currTotalCost_"], ["matColumnDef", "prevPerPso"], ["mat-footer-cell", "", "class", "text-right", 4, "matFooterCellDef"], ["matColumnDef", "prevTotalUnits"], ["matColumnDef", "prevTotalCost"], ["matColumnDef", "prevDispatched"], ["matColumnDef", "prevCumulativeDispatch"], ["matColumnDef", "previousYearFigures"], ["mat-footer-row", "", "class", "table-row", 4, "matFooterRowDef"], ["mat-footer-cell", ""], ["mat-cell", "", 1, "green"], ["appNumbersOnly", "", "step", "0.01", "matInput", "", 1, "text-right", 3, "ngModelChange", "change", "value", "ngModel", "ngModelOptions"], ["appNumbersOnly", "", "step", "0.01", "matInput", "", 1, "text-right", 3, "ngModelChange", "ngModel", "ngModelOptions"], ["step", "0.01", "mat-cell", "", 1, "text-right"], ["step", "0.01", "mat-footer-cell", "", 1, "text-right"], ["mat-header-cell", "", "readonly", "", 1, "lightViolet"], ["mat-footer-cell", "", 1, "text-right"], ["mat-footer-row", "", 1, "table-row"], ["type", "button", "matTooltip", "Click to Discard Current Saved product", 3, "click"]],
      template: function AnnualAllocationEntryComponent_Template(rf, ctx) {
        if (rf & 1) {
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "form", 0)(1, "div", 1)(2, "mat-accordion")(3, "mat-expansion-panel", 2)(4, "mat-expansion-panel-header")(5, "mat-panel-title")(6, "b");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](7, " Annual Sample Plan");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](8, "mat-panel-description");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](9, " Document Number : ");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](10, "b");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](11);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()()();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](12, "div", 3)(13, "div", 4)(14, "div", 5)(15, "mat-label", 6)(16, "b");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](17, "Financial Year :");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](18, "mat-label", 6);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](19);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelement"](20, "mat-label", 6);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](21, "mat-label", 6)(22, "b");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](23, "Date of Sample Plan :");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](24, "mat-label", 6);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](25);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()()()();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](26, "div", 7)(27, "div", 4)(28, "div", 5)(29, "mat-form-field", 8)(30, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](31);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](32, "mat-select", 9);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵlistener"]("selectionChange", function AnnualAllocationEntryComponent_Template_mat_select_selectionChange_32_listener() {
            return ctx.setEmpDetails();
          });
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](33, AnnualAllocationEntryComponent_mat_option_33_Template, 2, 5, "mat-option", 10);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](34, "mat-form-field", 8)(35, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](36, "Division");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](37, "mat-select", 11);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵlistener"]("selectionChange", function AnnualAllocationEntryComponent_Template_mat_select_selectionChange_37_listener() {
            return ctx.checkSubTeamExist();
          });
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](38, AnnualAllocationEntryComponent_mat_option_38_Template, 2, 7, "mat-option", 10);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](39, "mat-form-field", 12)(40, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](41, "Team");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](42, "mat-select", 13);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵlistener"]("selectionChange", function AnnualAllocationEntryComponent_Template_mat_select_selectionChange_42_listener() {
            return ctx.resetBrand();
          });
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](43, AnnualAllocationEntryComponent_mat_option_43_Template, 2, 2, "mat-option", 10);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](44, "mat-form-field", 8)(45, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](46, "Product Type");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](47, "mat-select", 14);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵlistener"]("selectionChange", function AnnualAllocationEntryComponent_Template_mat_select_selectionChange_47_listener() {
            return ctx.setBrands();
          });
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](48, AnnualAllocationEntryComponent_mat_option_48_Template, 2, 2, "mat-option", 10);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](49, "mat-form-field", 8)(50, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](51, "My Brands");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](52, "mat-select", 15);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵlistener"]("selectionChange", function AnnualAllocationEntryComponent_Template_mat_select_selectionChange_52_listener() {
            return ctx.setProductList();
          });
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](53, "mat-option", 16);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](54, "All");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](55, AnnualAllocationEntryComponent_mat_option_55_Template, 2, 2, "mat-option", 10);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](56, "mat-form-field", 8)(57, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](58, "Frequency");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](59, "mat-select", 17);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](60, AnnualAllocationEntryComponent_mat_option_60_Template, 2, 2, "mat-option", 10);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()()()()()()();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](61, "div", 5)(62, "div", 18)(63, "table", 19);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerStart"](64, 20);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](65, AnnualAllocationEntryComponent_th_65_Template, 1, 2, "th", 21)(66, AnnualAllocationEntryComponent_td_66_Template, 2, 2, "td", 22);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerStart"](67, 23);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](68, AnnualAllocationEntryComponent_th_68_Template, 3, 1, "th", 24);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerStart"](69, 25);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](70, AnnualAllocationEntryComponent_th_70_Template, 1, 2, "th", 21)(71, AnnualAllocationEntryComponent_td_71_Template, 6, 1, "td", 26);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerStart"](72, 27);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](73, AnnualAllocationEntryComponent_th_73_Template, 3, 1, "th", 24);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerStart"](74, 28);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](75, AnnualAllocationEntryComponent_th_75_Template, 1, 2, "th", 21)(76, AnnualAllocationEntryComponent_td_76_Template, 5, 1, "td", 26);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerStart"](77, 29);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](78, AnnualAllocationEntryComponent_th_78_Template, 3, 1, "th", 24);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerStart"](79, 30);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](80, AnnualAllocationEntryComponent_th_80_Template, 1, 2, "th", 21)(81, AnnualAllocationEntryComponent_td_81_Template, 2, 3, "td", 31);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerStart"](82, 32);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](83, AnnualAllocationEntryComponent_th_83_Template, 3, 1, "th", 24);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerStart"](84, 33);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](85, AnnualAllocationEntryComponent_th_85_Template, 1, 2, "th", 21)(86, AnnualAllocationEntryComponent_td_86_Template, 2, 3, "td", 31);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerStart"](87, 34);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](88, AnnualAllocationEntryComponent_th_88_Template, 3, 1, "th", 24);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerStart"](89, 35);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](90, AnnualAllocationEntryComponent_th_90_Template, 1, 2, "th", 21)(91, AnnualAllocationEntryComponent_td_91_Template, 2, 3, "td", 31);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerStart"](92, 36);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](93, AnnualAllocationEntryComponent_th_93_Template, 3, 1, "th", 24);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerStart"](94, 37);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](95, AnnualAllocationEntryComponent_th_95_Template, 3, 0, "th", 38)(96, AnnualAllocationEntryComponent_td_96_Template, 2, 1, "td", 39);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerStart"](97, 40);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](98, AnnualAllocationEntryComponent_th_98_Template, 3, 0, "th", 38)(99, AnnualAllocationEntryComponent_td_99_Template, 2, 3, "td", 26);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerStart"](100, 41);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](101, AnnualAllocationEntryComponent_th_101_Template, 3, 0, "th", 38)(102, AnnualAllocationEntryComponent_td_102_Template, 2, 3, "td", 26);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerStart"](103, 42);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](104, AnnualAllocationEntryComponent_th_104_Template, 3, 1, "th", 38)(105, AnnualAllocationEntryComponent_td_105_Template, 2, 3, "td", 39);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerStart"](106, 43);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](107, AnnualAllocationEntryComponent_th_107_Template, 3, 0, "th", 38)(108, AnnualAllocationEntryComponent_td_108_Template, 2, 3, "td", 26);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerStart"](109, 44);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](110, AnnualAllocationEntryComponent_th_110_Template, 3, 0, "th", 38)(111, AnnualAllocationEntryComponent_td_111_Template, 2, 3, "td", 26);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerStart"](112, 45);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](113, AnnualAllocationEntryComponent_th_113_Template, 3, 1, "th", 38)(114, AnnualAllocationEntryComponent_td_114_Template, 2, 3, "td", 26);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerStart"](115, 46);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](116, AnnualAllocationEntryComponent_th_116_Template, 1, 2, "th", 21)(117, AnnualAllocationEntryComponent_td_117_Template, 2, 1, "td", 47);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerStart"](118, 48);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](119, AnnualAllocationEntryComponent_th_119_Template, 3, 1, "th", 24);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerStart"](120, 49);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](121, AnnualAllocationEntryComponent_th_121_Template, 3, 1, "th", 38);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerStart"](122, 50);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](123, AnnualAllocationEntryComponent_th_123_Template, 3, 1, "th", 38);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](124, AnnualAllocationEntryComponent_tr_124_Template, 1, 0, "tr", 51)(125, AnnualAllocationEntryComponent_tr_125_Template, 1, 0, "tr", 52)(126, AnnualAllocationEntryComponent_tr_126_Template, 1, 0, "tr", 53);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()()();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](127, AnnualAllocationEntryComponent_div_127_Template, 59, 7, "div", 54);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](128, "div", 55)(129, "button", 56);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵlistener"]("click", function AnnualAllocationEntryComponent_Template_button_click_129_listener() {
            return ctx.onNewAllocation();
          });
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](130, "New");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](131, AnnualAllocationEntryComponent_button_131_Template, 2, 3, "button", 57);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](132, "button", 58);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵlistener"]("click", function AnnualAllocationEntryComponent_Template_button_click_132_listener() {
            return ctx.getAnnualPlanEnteredProductDetail();
          });
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](133, "View & Send for Approval");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](134, AnnualAllocationEntryComponent_button_134_Template, 2, 3, "button", 59);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](135, "button", 60);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](136, "Exit");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()()()();
        }
        if (rf & 2) {
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("formGroup", ctx.form);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("expanded", true);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"](8);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtextInterpolate"](ctx.allocDocNo);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"](8);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtextInterpolate"](ctx.financialYear);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"](6);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtextInterpolate"](ctx.allocationDate);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵclassMapInterpolate1"]("mt-1 ", ctx.constants.page_div_class, "");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("ngClass", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"](2);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtextInterpolate"](ctx.enterFor);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("disabled", ctx.disableEnterFor);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("ngForOf", ctx.managerList);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("ngClass", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("disabled", ctx.headerFreezed);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("ngForOf", ctx.teams);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("ngClass", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("disabled", ctx.subTeamFreezed);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("ngForOf", ctx.subTeamList);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("ngClass", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("disabled", ctx.disableProdType);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("ngForOf", ctx.planType);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("ngClass", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"](4);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("value", 0);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"](2);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("ngForOf", ctx.brands);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("ngClass", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"](4);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("ngForOf", ctx.frequency);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("dataSource", ctx.productList);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"](61);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("matHeaderRowDef", _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵpureFunction0"](43, _c0));
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("matHeaderRowDef", ctx.displayedColumns)("matHeaderRowDefSticky", true);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("matRowDefColumns", ctx.displayedColumns);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("ngIf", ctx.showTable);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"](2);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵclassMapInterpolate1"]("mr-r-10 grad-btn ", ctx.constants.exit_btn_class, "");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"](2);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("ngIf", ctx.allowSave);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵclassMapInterpolate1"]("mr-r-10 grad-btn ", ctx.constants.exit_btn_class, "");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"](2);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("ngIf", ctx.allowSave);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵclassMapInterpolate1"]("mr-r-10 grad-btn ", ctx.constants.exit_btn_class, "");
        }
      },
      dependencies: [_angular_router__WEBPACK_IMPORTED_MODULE_11__.RouterLink, _angular_common__WEBPACK_IMPORTED_MODULE_12__.NgClass, _angular_common__WEBPACK_IMPORTED_MODULE_12__.NgForOf, _angular_common__WEBPACK_IMPORTED_MODULE_12__.NgIf, _angular_common__WEBPACK_IMPORTED_MODULE_12__.NgStyle, _angular_forms__WEBPACK_IMPORTED_MODULE_7__["ɵNgNoValidate"], _angular_forms__WEBPACK_IMPORTED_MODULE_7__.DefaultValueAccessor, _angular_forms__WEBPACK_IMPORTED_MODULE_7__.CheckboxControlValueAccessor, _angular_forms__WEBPACK_IMPORTED_MODULE_7__.NgControlStatus, _angular_forms__WEBPACK_IMPORTED_MODULE_7__.NgControlStatusGroup, _angular_forms__WEBPACK_IMPORTED_MODULE_7__.NgModel, _angular_forms__WEBPACK_IMPORTED_MODULE_7__.FormGroupDirective, _angular_forms__WEBPACK_IMPORTED_MODULE_7__.FormControlName, _angular_material_form_field__WEBPACK_IMPORTED_MODULE_13__.MatFormField, _angular_material_form_field__WEBPACK_IMPORTED_MODULE_13__.MatLabel, _angular_flex_layout_flex__WEBPACK_IMPORTED_MODULE_14__.DefaultLayoutDirective, _angular_flex_layout_flex__WEBPACK_IMPORTED_MODULE_14__.DefaultFlexDirective, _angular_flex_layout_extended__WEBPACK_IMPORTED_MODULE_15__.DefaultClassDirective, _angular_flex_layout_extended__WEBPACK_IMPORTED_MODULE_15__.DefaultStyleDirective, _angular_material_input__WEBPACK_IMPORTED_MODULE_16__.MatInput, _angular_material_expansion__WEBPACK_IMPORTED_MODULE_17__.MatAccordion, _angular_material_expansion__WEBPACK_IMPORTED_MODULE_17__.MatExpansionPanel, _angular_material_expansion__WEBPACK_IMPORTED_MODULE_17__.MatExpansionPanelHeader, _angular_material_expansion__WEBPACK_IMPORTED_MODULE_17__.MatExpansionPanelTitle, _angular_material_expansion__WEBPACK_IMPORTED_MODULE_17__.MatExpansionPanelDescription, _angular_material_select__WEBPACK_IMPORTED_MODULE_18__.MatSelect, _angular_material_core__WEBPACK_IMPORTED_MODULE_19__.MatOption, _angular_material_table__WEBPACK_IMPORTED_MODULE_8__.MatTable, _angular_material_table__WEBPACK_IMPORTED_MODULE_8__.MatHeaderCellDef, _angular_material_table__WEBPACK_IMPORTED_MODULE_8__.MatHeaderRowDef, _angular_material_table__WEBPACK_IMPORTED_MODULE_8__.MatColumnDef, _angular_material_table__WEBPACK_IMPORTED_MODULE_8__.MatCellDef, _angular_material_table__WEBPACK_IMPORTED_MODULE_8__.MatRowDef, _angular_material_table__WEBPACK_IMPORTED_MODULE_8__.MatFooterCellDef, _angular_material_table__WEBPACK_IMPORTED_MODULE_8__.MatFooterRowDef, _angular_material_table__WEBPACK_IMPORTED_MODULE_8__.MatHeaderCell, _angular_material_table__WEBPACK_IMPORTED_MODULE_8__.MatCell, _angular_material_table__WEBPACK_IMPORTED_MODULE_8__.MatFooterCell, _angular_material_table__WEBPACK_IMPORTED_MODULE_8__.MatHeaderRow, _angular_material_table__WEBPACK_IMPORTED_MODULE_8__.MatRow, _angular_material_table__WEBPACK_IMPORTED_MODULE_8__.MatFooterRow, _angular_material_tooltip__WEBPACK_IMPORTED_MODULE_20__.MatTooltip, _directives_utility_numbers_only_directive__WEBPACK_IMPORTED_MODULE_4__.NumbersOnlyDirective],
      styles: [_c7]
    });
  }
  return AnnualAllocationEntryComponent;
})();
let AnnualDetailComponent = /*#__PURE__*/(() => {
  class AnnualDetailComponent {
    dialogRef;
    data;
    fb;
    medicoUtility;
    medicoService;
    allocationService;
    dialog;
    userService;
    constants = new _classes_constants__WEBPACK_IMPORTED_MODULE_0__.Constants();
    session;
    brandList = new Array();
    entredBrands = new Array();
    brandIds;
    checkUser;
    month1;
    month2;
    month3;
    month4;
    month5;
    month6;
    month7;
    month8;
    month9;
    month10;
    month11;
    month12;
    companycode;
    constructor(dialogRef, data, fb, medicoUtility, medicoService, allocationService, dialog, userService) {
      this.dialogRef = dialogRef;
      this.data = data;
      this.fb = fb;
      this.medicoUtility = medicoUtility;
      this.medicoService = medicoService;
      this.allocationService = allocationService;
      this.dialog = dialog;
      this.userService = userService;
      this.brandList = data.enteredBrandsEnt;
      this.userService.getcompanydata().subscribe(res => {
        this.companycode = res.company.company.trim();
        var comp = this.companycode;
        //console.log("comp:::"+comp);
        if (comp == "PFZ") {
          this.month1 = "Dec";
          this.month2 = "Jan";
          this.month3 = "Feb";
          this.month4 = "Mar";
          this.month5 = "Apr";
          this.month6 = "May";
          this.month7 = "Jun";
          this.month8 = "Jul";
          this.month9 = "Aug";
          this.month10 = "Sep";
          this.month11 = "Oct";
          this.month12 = "Nov";
        }
        if (comp == "SER") {
          this.month1 = "Apr";
          this.month2 = "May";
          this.month3 = "Jun";
          this.month4 = "Jul";
          this.month5 = "Aug";
          this.month6 = "Sep";
          this.month7 = "Oct";
          this.month8 = "Nov";
          this.month9 = "Dec";
          this.month10 = "Jan";
          this.month11 = "Feb";
          this.month12 = "Mar";
        }
      });
    }
    paginator;
    displayedColumns = ["edit", "delete", "product", "dec", "jan", "feb", "march", "april", "may", "june", "july", "aug", "sept", "oct", "nov", "cog", "totalQty", "totalValue"];
    sort;
    applyFilter(filterValue) {
      this.data.list.filter = filterValue.trim().toLowerCase();
    }
    submitforApproval(approval) {
      this.dialogRef.close({
        action: approval
      });
    }
    onClickOfEdit(action, detailId) {
      this.dialogRef.close({
        action: action,
        detailId: detailId
      });
    }
    static ɵfac = function AnnualDetailComponent_Factory(__ngFactoryType__) {
      return new (__ngFactoryType__ || AnnualDetailComponent)(_angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵdirectiveInject"](_angular_material_dialog__WEBPACK_IMPORTED_MODULE_9__.MatDialogRef), _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵdirectiveInject"](_angular_material_dialog__WEBPACK_IMPORTED_MODULE_9__.MAT_DIALOG_DATA), _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵdirectiveInject"](_angular_forms__WEBPACK_IMPORTED_MODULE_7__.FormBuilder), _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵdirectiveInject"](_classes_medico_utility__WEBPACK_IMPORTED_MODULE_1__.MedicoUtility), _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵdirectiveInject"](_services_medico_medico_service__WEBPACK_IMPORTED_MODULE_2__.MedicoService), _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵdirectiveInject"](src_app_services_allocation_allocation_service__WEBPACK_IMPORTED_MODULE_3__.AllocationService), _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵdirectiveInject"](_angular_material_dialog__WEBPACK_IMPORTED_MODULE_9__.MatDialog), _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵdirectiveInject"](src_app_services_user_user_service__WEBPACK_IMPORTED_MODULE_5__.UserService));
    };
    static ɵcmp = /*@__PURE__*/_angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵdefineComponent"]({
      type: AnnualDetailComponent,
      selectors: [["annual-details-dialog"]],
      viewQuery: function AnnualDetailComponent_Query(rf, ctx) {
        if (rf & 1) {
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵviewQuery"](_angular_material_paginator__WEBPACK_IMPORTED_MODULE_10__.MatPaginator, 5);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵviewQuery"](_angular_material_sort__WEBPACK_IMPORTED_MODULE_21__.MatSort, 5);
        }
        if (rf & 2) {
          let _t;
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵqueryRefresh"](_t = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵloadQuery"]()) && (ctx.paginator = _t.first);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵqueryRefresh"](_t = _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵloadQuery"]()) && (ctx.sort = _t.first);
        }
      },
      decls: 66,
      vars: 9,
      consts: [["mat-dialog-title", ""], ["fxLayout", "row", "fxLayout.lt-md", "column", 1, "padding"], [2, "text-align", "left", "padding", "1%"], [2, "font-size", "15px", "font-weight", "bold", "color", "red"], [1, "table-container", "mat-elevation-z2"], ["mat-table", "", "matSort", "", 1, "table-borders-right", "thin-th", "mat-td-p", "mat-th-p", 2, "width", "1500px", "overflow", "auto", 3, "dataSource"], ["matColumnDef", "edit", 3, "sticky"], ["mat-header-cell", "", "class", "lightViolet", 4, "matHeaderCellDef"], ["mat-cell", "", "matTooltip", "Click to Edit", 3, "click", 4, "matCellDef"], ["matColumnDef", "delete", 3, "sticky"], ["matColumnDef", "product", 3, "sticky"], ["mat-cell", "", 3, "matTooltip", 4, "matCellDef"], ["matColumnDef", "dec"], ["mat-cell", "", "appNumbersOnly", "", "step", "0.01", "class", "text-right", 4, "matCellDef"], ["matColumnDef", "jan"], ["matColumnDef", "feb"], ["matColumnDef", "march"], ["matColumnDef", "april"], ["matColumnDef", "may"], ["matColumnDef", "june"], ["matColumnDef", "july"], ["matColumnDef", "aug"], ["matColumnDef", "sept"], ["matColumnDef", "oct"], ["matColumnDef", "nov"], ["matColumnDef", "cog"], ["mat-cell", "", "appNumbersOnly", "", "class", "text-right", 4, "matCellDef"], ["matColumnDef", "totalQty"], ["matColumnDef", "totalValue"], ["mat-header-row", "", "class", "table-row", "mat-sort-header", "", 4, "matHeaderRowDef", "matHeaderRowDefSticky"], ["mat-row", "", "class", "table-row", 3, "ngClass", 4, "matRowDef", "matRowDefColumns"], ["fxLayout", "row", "class", "mt-1 mb-1", 4, "ngIf"], ["mat-header-cell", "", 1, "lightViolet"], ["mat-cell", "", "matTooltip", "Click to Edit", 3, "click"], ["mat-cell", "", 3, "matTooltip"], ["mat-cell", "", "appNumbersOnly", "", "step", "0.01", 1, "text-right"], ["mat-cell", "", "appNumbersOnly", "", 1, "text-right"], ["mat-header-row", "", "mat-sort-header", "", 1, "table-row"], ["mat-row", "", 1, "table-row", 3, "ngClass"], ["fxLayout", "row", 1, "mt-1", "mb-1"], ["type", "button", 3, "class", "click", 4, "ngIf"], ["type", "button", 3, "click"]],
      template: function AnnualDetailComponent_Template(rf, ctx) {
        if (rf & 1) {
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](0, "h1", 0);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](1, "Entered Products For Selected Team");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](2, "div", 1)(3, "div", 2)(4, "span", 3);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtext"](5);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()()();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementStart"](6, "div", 1)(7, "div", 4)(8, "table", 5);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerStart"](9, 6);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](10, AnnualDetailComponent_th_10_Template, 1, 0, "th", 7)(11, AnnualDetailComponent_td_11_Template, 3, 0, "td", 8);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerStart"](12, 9);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](13, AnnualDetailComponent_th_13_Template, 1, 0, "th", 7)(14, AnnualDetailComponent_td_14_Template, 3, 0, "td", 8);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerStart"](15, 10);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](16, AnnualDetailComponent_th_16_Template, 3, 0, "th", 7)(17, AnnualDetailComponent_td_17_Template, 3, 6, "td", 11);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerStart"](18, 12);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](19, AnnualDetailComponent_th_19_Template, 3, 1, "th", 7)(20, AnnualDetailComponent_td_20_Template, 2, 1, "td", 13);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerStart"](21, 14);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](22, AnnualDetailComponent_th_22_Template, 3, 1, "th", 7)(23, AnnualDetailComponent_td_23_Template, 2, 1, "td", 13);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerStart"](24, 15);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](25, AnnualDetailComponent_th_25_Template, 3, 1, "th", 7)(26, AnnualDetailComponent_td_26_Template, 2, 1, "td", 13);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerStart"](27, 16);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](28, AnnualDetailComponent_th_28_Template, 3, 1, "th", 7)(29, AnnualDetailComponent_td_29_Template, 2, 1, "td", 13);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerStart"](30, 17);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](31, AnnualDetailComponent_th_31_Template, 3, 1, "th", 7)(32, AnnualDetailComponent_td_32_Template, 2, 1, "td", 13);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerStart"](33, 18);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](34, AnnualDetailComponent_th_34_Template, 3, 1, "th", 7)(35, AnnualDetailComponent_td_35_Template, 2, 1, "td", 13);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerStart"](36, 19);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](37, AnnualDetailComponent_th_37_Template, 3, 1, "th", 7)(38, AnnualDetailComponent_td_38_Template, 2, 1, "td", 13);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerStart"](39, 20);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](40, AnnualDetailComponent_th_40_Template, 3, 1, "th", 7)(41, AnnualDetailComponent_td_41_Template, 2, 1, "td", 13);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerStart"](42, 21);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](43, AnnualDetailComponent_th_43_Template, 3, 1, "th", 7)(44, AnnualDetailComponent_td_44_Template, 2, 1, "td", 13);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerStart"](45, 22);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](46, AnnualDetailComponent_th_46_Template, 3, 1, "th", 7)(47, AnnualDetailComponent_td_47_Template, 2, 1, "td", 13);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerStart"](48, 23);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](49, AnnualDetailComponent_th_49_Template, 3, 1, "th", 7)(50, AnnualDetailComponent_td_50_Template, 2, 1, "td", 13);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerStart"](51, 24);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](52, AnnualDetailComponent_th_52_Template, 3, 1, "th", 7)(53, AnnualDetailComponent_td_53_Template, 2, 1, "td", 13);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerStart"](54, 25);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](55, AnnualDetailComponent_th_55_Template, 3, 0, "th", 7)(56, AnnualDetailComponent_td_56_Template, 2, 1, "td", 26);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerStart"](57, 27);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](58, AnnualDetailComponent_th_58_Template, 3, 0, "th", 7)(59, AnnualDetailComponent_td_59_Template, 2, 1, "td", 26);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerStart"](60, 28);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](61, AnnualDetailComponent_th_61_Template, 3, 0, "th", 7)(62, AnnualDetailComponent_td_62_Template, 2, 1, "td", 26);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](63, AnnualDetailComponent_tr_63_Template, 1, 0, "tr", 29)(64, AnnualDetailComponent_tr_64_Template, 1, 4, "tr", 30);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵelementEnd"]()()();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtemplate"](65, AnnualDetailComponent_div_65_Template, 3, 2, "div", 31);
        }
        if (rf & 2) {
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"](5);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵtextInterpolate1"]("Note :", ctx.data.msg, "");
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("dataSource", ctx.data.list);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("sticky", true);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("sticky", true);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("sticky", true);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"](48);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("matHeaderRowDef", ctx.displayedColumns)("matHeaderRowDefSticky", true);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("matRowDefColumns", ctx.displayedColumns);
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵproperty"]("ngIf", ctx.data.allowSave);
        }
      },
      dependencies: [_angular_common__WEBPACK_IMPORTED_MODULE_12__.NgClass, _angular_common__WEBPACK_IMPORTED_MODULE_12__.NgIf, _angular_flex_layout_flex__WEBPACK_IMPORTED_MODULE_14__.DefaultLayoutDirective, _angular_flex_layout_extended__WEBPACK_IMPORTED_MODULE_15__.DefaultClassDirective, _angular_material_dialog__WEBPACK_IMPORTED_MODULE_9__.MatDialogTitle, _angular_material_table__WEBPACK_IMPORTED_MODULE_8__.MatTable, _angular_material_table__WEBPACK_IMPORTED_MODULE_8__.MatHeaderCellDef, _angular_material_table__WEBPACK_IMPORTED_MODULE_8__.MatHeaderRowDef, _angular_material_table__WEBPACK_IMPORTED_MODULE_8__.MatColumnDef, _angular_material_table__WEBPACK_IMPORTED_MODULE_8__.MatCellDef, _angular_material_table__WEBPACK_IMPORTED_MODULE_8__.MatRowDef, _angular_material_table__WEBPACK_IMPORTED_MODULE_8__.MatHeaderCell, _angular_material_table__WEBPACK_IMPORTED_MODULE_8__.MatCell, _angular_material_table__WEBPACK_IMPORTED_MODULE_8__.MatHeaderRow, _angular_material_table__WEBPACK_IMPORTED_MODULE_8__.MatRow, _angular_material_tooltip__WEBPACK_IMPORTED_MODULE_20__.MatTooltip, _directives_utility_numbers_only_directive__WEBPACK_IMPORTED_MODULE_4__.NumbersOnlyDirective, _angular_common__WEBPACK_IMPORTED_MODULE_12__.SlicePipe],
      styles: [_c7]
    });
  }
  return AnnualDetailComponent;
})();

/***/ }),

/***/ 96070:
/*!*************************************************************************************************************!*\
  !*** ./src/app/components/transaction/monthly-allocation-entry-pg/monthly-allocation-entry-pg.component.ts ***!
  \*************************************************************************************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   AllocEntModifyComponentpg: () => (/* binding */ AllocEntModifyComponentpg),
/* harmony export */   AllocRegenerateComponentpg: () => (/* binding */ AllocRegenerateComponentpg),
/* harmony export */   FieldstaffDetailsComponentpg: () => (/* binding */ FieldstaffDetailsComponentpg),
/* harmony export */   MonthlyAllocationEntryPgComponent: () => (/* binding */ MonthlyAllocationEntryPgComponent)
/* harmony export */ });
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! @angular/forms */ 34456);
/* harmony import */ var _angular_material_dialog__WEBPACK_IMPORTED_MODULE_8__ = __webpack_require__(/*! @angular/material/dialog */ 12587);
/* harmony import */ var _angular_material_paginator__WEBPACK_IMPORTED_MODULE_9__ = __webpack_require__(/*! @angular/material/paginator */ 24624);
/* harmony import */ var _angular_material_sort__WEBPACK_IMPORTED_MODULE_20__ = __webpack_require__(/*! @angular/material/sort */ 22047);
/* harmony import */ var _angular_material_table__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! @angular/material/table */ 77697);
/* harmony import */ var src_app_classes_constants__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! src/app/classes/constants */ 5556);
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! @angular/core */ 37580);
/* harmony import */ var src_app_classes_medico_utility__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! src/app/classes/medico-utility */ 79955);
/* harmony import */ var src_app_services_medico_medico_service__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! src/app/services/medico/medico.service */ 353);
/* harmony import */ var src_app_services_allocation_allocation_service__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! src/app/services/allocation/allocation.service */ 81235);
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_10__ = __webpack_require__(/*! @angular/router */ 95072);
/* harmony import */ var _angular_common__WEBPACK_IMPORTED_MODULE_11__ = __webpack_require__(/*! @angular/common */ 60316);
/* harmony import */ var _angular_material_form_field__WEBPACK_IMPORTED_MODULE_12__ = __webpack_require__(/*! @angular/material/form-field */ 24950);
/* harmony import */ var _angular_flex_layout_flex__WEBPACK_IMPORTED_MODULE_13__ = __webpack_require__(/*! @angular/flex-layout/flex */ 91447);
/* harmony import */ var _angular_flex_layout_extended__WEBPACK_IMPORTED_MODULE_14__ = __webpack_require__(/*! @angular/flex-layout/extended */ 52913);
/* harmony import */ var _angular_material_input__WEBPACK_IMPORTED_MODULE_15__ = __webpack_require__(/*! @angular/material/input */ 95541);
/* harmony import */ var _angular_material_expansion__WEBPACK_IMPORTED_MODULE_16__ = __webpack_require__(/*! @angular/material/expansion */ 19322);
/* harmony import */ var _angular_material_select__WEBPACK_IMPORTED_MODULE_17__ = __webpack_require__(/*! @angular/material/select */ 25175);
/* harmony import */ var _angular_material_core__WEBPACK_IMPORTED_MODULE_18__ = __webpack_require__(/*! @angular/material/core */ 74646);
/* harmony import */ var _angular_material_tooltip__WEBPACK_IMPORTED_MODULE_19__ = __webpack_require__(/*! @angular/material/tooltip */ 80640);
/* harmony import */ var _directives_utility_numbers_only_directive__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ../../../directives/utility/numbers-only.directive */ 22128);
/* harmony import */ var _angular_material_radio__WEBPACK_IMPORTED_MODULE_21__ = __webpack_require__(/*! @angular/material/radio */ 53804);

























const _c0 = (a0, a1) => [a0, a1];
const _c1 = () => ({
  standalone: true
});
const _c2 = () => ["productDetails", "allocation", "stock"];
const _c3 = () => ["productCode_", "brand_", "prodDescription_", "pack_", "minAllocationQty_", "te_", "dm_", "rbm_", "tm_", "sm_", "totatlAllocationQty_", "totalStock_", "transitStock_", "allocationSoFar_", "balanceStock_"];
const _c4 = () => ["teCount", "dmCount", "rmbCount", "tmCount", "smCount"];
const _c5 = () => ["no1", "no2", "no3", "no4", "no5", "no6", "no7", "no8", "no9", "no10"];
const _c6 = () => ({
  "display": "none"
});
const _c7 = a0 => ({
  "negative": a0
});
function MonthlyAllocationEntryPgComponent_mat_option_37_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "mat-option", 19);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const m_r1 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("value", _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵpureFunction2"](2, _c0, m_r1.mgr_id, m_r1.mgrEmpId));
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](m_r1.mgr_name);
  }
}
function MonthlyAllocationEntryPgComponent_mat_option_42_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "mat-option", 19);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const t_r2 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("value", t_r2.div_id);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate1"]("", t_r2.div_disp_nm, " ");
  }
}
function MonthlyAllocationEntryPgComponent_mat_option_47_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "mat-option", 19);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const t_r3 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("value", t_r3.team_code);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate1"]("", t_r3.team_name, " ");
  }
}
function MonthlyAllocationEntryPgComponent_mat_option_52_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "mat-option", 19);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const l_r4 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("value", l_r4.key);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](l_r4.value);
  }
}
function MonthlyAllocationEntryPgComponent_mat_option_57_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "mat-option", 19);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const l_r5 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("value", l_r5.key);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](l_r5.value);
  }
}
function MonthlyAllocationEntryPgComponent_mat_option_62_Template(rf, ctx) {
  if (rf & 1) {
    const _r6 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵgetCurrentView"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "mat-option", 39);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵlistener"]("click", function MonthlyAllocationEntryPgComponent_mat_option_62_Template_mat_option_click_0_listener() {
      const p_r7 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵrestoreView"](_r6).$implicit;
      const ctx_r7 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"]();
      return _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵresetView"](ctx_r7.setBrandsAllocationCycle(p_r7.sgprmdet_disp_nm));
    });
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const p_r7 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("value", p_r7.sgprmdet_id);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](p_r7.sgprmdet_disp_nm);
  }
}
function MonthlyAllocationEntryPgComponent_mat_option_69_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "mat-option", 19);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const b_r9 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("value", b_r9.smp_sa_prod_group);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](b_r9.sa_group_name);
  }
}
function MonthlyAllocationEntryPgComponent_mat_option_75_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "mat-option", 19);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const a_r10 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("value", a_r10.key);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](a_r10.value);
  }
}
function MonthlyAllocationEntryPgComponent_mat_option_82_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "mat-option", 19);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const a_r11 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("value", a_r11.allocationById);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](a_r11.allocationByDesc);
  }
}
function MonthlyAllocationEntryPgComponent_mat_option_87_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "mat-option", 19);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const a_r12 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("value", a_r12.key);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](a_r12.value);
  }
}
function MonthlyAllocationEntryPgComponent_mat_option_92_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "mat-option", 19);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const a_r13 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("value", a_r13.key);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](a_r13.value);
  }
}
function MonthlyAllocationEntryPgComponent_mat_option_97_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "mat-option", 19);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const a_r14 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("value", a_r14.key);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](a_r14.value);
  }
}
function MonthlyAllocationEntryPgComponent_mat_option_102_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "mat-option", 19);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const l_r15 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("value", _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵpureFunction2"](2, _c0, l_r15.period_fin_year, l_r15.period_code));
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](l_r15.period_name);
  }
}
function MonthlyAllocationEntryPgComponent_mat_option_107_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "mat-option", 19);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const l_r16 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("value", l_r16.period_code);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](l_r16.period_name);
  }
}
function MonthlyAllocationEntryPgComponent_div_108_div_13_Template(rf, ctx) {
  if (rf & 1) {
    const _r17 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵgetCurrentView"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "div", 44)(1, "mat-label", 45)(2, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](3, "TM Qty :");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](4, "mat-label", 46)(5, "input", 47);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtwoWayListener"]("ngModelChange", function MonthlyAllocationEntryPgComponent_div_108_div_13_Template_input_ngModelChange_5_listener($event) {
      _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵrestoreView"](_r17);
      const ctx_r7 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"](2);
      _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtwoWayBindingSet"](ctx_r7.tmqty, $event) || (ctx_r7.tmqty = $event);
      return _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵresetView"]($event);
    });
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](6, "mat-label", 41);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](7, "mat-label", 45)(8, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](9, "SM Qty :");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](10, "mat-label", 46)(11, "input", 47);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtwoWayListener"]("ngModelChange", function MonthlyAllocationEntryPgComponent_div_108_div_13_Template_input_ngModelChange_11_listener($event) {
      _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵrestoreView"](_r17);
      const ctx_r7 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"](2);
      _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtwoWayBindingSet"](ctx_r7.smqty, $event) || (ctx_r7.smqty = $event);
      return _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵresetView"]($event);
    });
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()()();
  }
  if (rf & 2) {
    const ctx_r7 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](5);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtwoWayProperty"]("ngModel", ctx_r7.tmqty);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngModelOptions", _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵpureFunction0"](4, _c1));
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](6);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtwoWayProperty"]("ngModel", ctx_r7.smqty);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngModelOptions", _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵpureFunction0"](5, _c1));
  }
}
function MonthlyAllocationEntryPgComponent_div_108_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "div", 40)(1, "div", 4)(2, "div", 7)(3, "mat-label", 41)(4, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](5);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](6, "mat-label", 41)(7, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](8);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](9, "mat-label", 41)(10, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](11);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](12, "mat-label", 42);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](13, MonthlyAllocationEntryPgComponent_div_108_div_13_Template, 12, 6, "div", 43);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()()();
  }
  if (rf & 2) {
    const ctx_r7 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](5);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate1"]("PSO : ", ctx_r7.staffCount.msr, "");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](3);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate1"]("DM : ", ctx_r7.staffCount.Totalabm, "");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](3);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate1"]("RM : ", ctx_r7.staffCount.Totalrbm, "");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngIf", ctx_r7.showSmTm);
  }
}
function MonthlyAllocationEntryPgComponent_div_109_th_4_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](0, "th", 110);
  }
  if (rf & 2) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngStyle", _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵpureFunction0"](1, _c6));
  }
}
function MonthlyAllocationEntryPgComponent_div_109_td_5_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "td", 111);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const row_r18 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](row_r18.smp_prod_cd);
  }
}
function MonthlyAllocationEntryPgComponent_div_109_th_7_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "th", 112)(1, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](2, "F Code");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵattribute"]("rowspan", 4);
  }
}
function MonthlyAllocationEntryPgComponent_div_109_th_9_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](0, "th", 110);
  }
  if (rf & 2) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngStyle", _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵpureFunction0"](1, _c6));
  }
}
function MonthlyAllocationEntryPgComponent_div_109_td_10_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "td", 111);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const row_r19 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](row_r19.sa_group_name);
  }
}
function MonthlyAllocationEntryPgComponent_div_109_th_12_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "th", 112)(1, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](2, "Brand");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵattribute"]("rowspan", 4);
  }
}
function MonthlyAllocationEntryPgComponent_div_109_th_14_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](0, "th", 110);
  }
  if (rf & 2) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngStyle", _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵpureFunction0"](1, _c6));
  }
}
function MonthlyAllocationEntryPgComponent_div_109_td_15_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "td", 113);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵpipe"](2, "slice");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const row_r20 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵpropertyInterpolate"]("matTooltip", row_r20.smp_prod_name);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](_angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵpipeBind3"](2, 2, row_r20.smp_prod_name, 0, 20));
  }
}
function MonthlyAllocationEntryPgComponent_div_109_th_17_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "th", 112)(1, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](2, "Product Description");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵattribute"]("rowspan", 4);
  }
}
function MonthlyAllocationEntryPgComponent_div_109_th_19_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](0, "th", 110);
  }
  if (rf & 2) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngStyle", _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵpureFunction0"](1, _c6));
  }
}
function MonthlyAllocationEntryPgComponent_div_109_td_20_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "td", 114);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const row_r21 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](row_r21.pack_disp_nm);
  }
}
function MonthlyAllocationEntryPgComponent_div_109_th_22_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "th", 112)(1, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](2, "Pack");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵattribute"]("rowspan", 4);
  }
}
function MonthlyAllocationEntryPgComponent_div_109_th_24_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](0, "th", 110);
  }
  if (rf & 2) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngStyle", _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵpureFunction0"](1, _c6));
  }
}
function MonthlyAllocationEntryPgComponent_div_109_td_25_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "td", 114);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const row_r22 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](row_r22.smp_min_alloc_qty);
  }
}
function MonthlyAllocationEntryPgComponent_div_109_th_27_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "th", 112)(1, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](2, "Min. Qty");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵattribute"]("rowspan", 4);
  }
}
function MonthlyAllocationEntryPgComponent_div_109_th_29_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](0, "th", 115);
  }
  if (rf & 2) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngStyle", _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵpureFunction0"](1, _c6));
  }
}
function MonthlyAllocationEntryPgComponent_div_109_td_30_Template(rf, ctx) {
  if (rf & 1) {
    const _r23 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵgetCurrentView"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "td", 116)(1, "input", 117);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtwoWayListener"]("ngModelChange", function MonthlyAllocationEntryPgComponent_div_109_td_30_Template_input_ngModelChange_1_listener($event) {
      const i_r24 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵrestoreView"](_r23).index;
      const ctx_r7 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"](2);
      _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtwoWayBindingSet"](ctx_r7.teModel[i_r24], $event) || (ctx_r7.teModel[i_r24] = $event);
      return _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵresetView"]($event);
    });
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵlistener"]("change", function MonthlyAllocationEntryPgComponent_div_109_td_30_Template_input_change_1_listener() {
      const i_r24 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵrestoreView"](_r23).index;
      const ctx_r7 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"](2);
      return _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵresetView"](ctx_r7.claculateFields(i_r24));
    });
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    const i_r24 = ctx.index;
    const ctx_r7 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtwoWayProperty"]("ngModel", ctx_r7.teModel[i_r24]);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngModelOptions", _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵpureFunction0"](2, _c1));
  }
}
function MonthlyAllocationEntryPgComponent_div_109_th_32_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "th", 118)(1, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](2, "TE");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵattribute"]("rowspan", 2);
  }
}
function MonthlyAllocationEntryPgComponent_div_109_th_34_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](0, "th", 115);
  }
  if (rf & 2) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngStyle", _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵpureFunction0"](1, _c6));
  }
}
function MonthlyAllocationEntryPgComponent_div_109_td_35_Template(rf, ctx) {
  if (rf & 1) {
    const _r25 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵgetCurrentView"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "td", 116)(1, "input", 117);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtwoWayListener"]("ngModelChange", function MonthlyAllocationEntryPgComponent_div_109_td_35_Template_input_ngModelChange_1_listener($event) {
      const i_r26 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵrestoreView"](_r25).index;
      const ctx_r7 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"](2);
      _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtwoWayBindingSet"](ctx_r7.dmModel[i_r26], $event) || (ctx_r7.dmModel[i_r26] = $event);
      return _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵresetView"]($event);
    });
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵlistener"]("change", function MonthlyAllocationEntryPgComponent_div_109_td_35_Template_input_change_1_listener() {
      const i_r26 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵrestoreView"](_r25).index;
      const ctx_r7 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"](2);
      return _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵresetView"](ctx_r7.claculateFields(i_r26));
    });
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    const i_r26 = ctx.index;
    const ctx_r7 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtwoWayProperty"]("ngModel", ctx_r7.dmModel[i_r26]);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngModelOptions", _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵpureFunction0"](2, _c1));
  }
}
function MonthlyAllocationEntryPgComponent_div_109_th_37_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "th", 118)(1, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](2, "DM");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵattribute"]("rowspan", 2);
  }
}
function MonthlyAllocationEntryPgComponent_div_109_th_39_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](0, "th", 110);
  }
  if (rf & 2) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngStyle", _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵpureFunction0"](1, _c6));
  }
}
function MonthlyAllocationEntryPgComponent_div_109_td_40_Template(rf, ctx) {
  if (rf & 1) {
    const _r27 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵgetCurrentView"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "td", 116)(1, "input", 117);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtwoWayListener"]("ngModelChange", function MonthlyAllocationEntryPgComponent_div_109_td_40_Template_input_ngModelChange_1_listener($event) {
      const i_r28 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵrestoreView"](_r27).index;
      const ctx_r7 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"](2);
      _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtwoWayBindingSet"](ctx_r7.rmbModel[i_r28], $event) || (ctx_r7.rmbModel[i_r28] = $event);
      return _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵresetView"]($event);
    });
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵlistener"]("change", function MonthlyAllocationEntryPgComponent_div_109_td_40_Template_input_change_1_listener() {
      const i_r28 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵrestoreView"](_r27).index;
      const ctx_r7 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"](2);
      return _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵresetView"](ctx_r7.claculateFields(i_r28));
    });
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    const i_r28 = ctx.index;
    const ctx_r7 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtwoWayProperty"]("ngModel", ctx_r7.rmbModel[i_r28]);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngModelOptions", _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵpureFunction0"](2, _c1));
  }
}
function MonthlyAllocationEntryPgComponent_div_109_th_42_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "th", 118)(1, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](2, "RBM");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵattribute"]("rowspan", 2);
  }
}
function MonthlyAllocationEntryPgComponent_div_109_th_44_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](0, "th", 110);
  }
  if (rf & 2) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngStyle", _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵpureFunction0"](1, _c6));
  }
}
function MonthlyAllocationEntryPgComponent_div_109_td_45_Template(rf, ctx) {
  if (rf & 1) {
    const _r29 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵgetCurrentView"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "td", 116)(1, "input", 117);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtwoWayListener"]("ngModelChange", function MonthlyAllocationEntryPgComponent_div_109_td_45_Template_input_ngModelChange_1_listener($event) {
      const i_r30 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵrestoreView"](_r29).index;
      const ctx_r7 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"](2);
      _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtwoWayBindingSet"](ctx_r7.tmModel[i_r30], $event) || (ctx_r7.tmModel[i_r30] = $event);
      return _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵresetView"]($event);
    });
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵlistener"]("change", function MonthlyAllocationEntryPgComponent_div_109_td_45_Template_input_change_1_listener() {
      const i_r30 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵrestoreView"](_r29).index;
      const ctx_r7 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"](2);
      return _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵresetView"](ctx_r7.claculateFieldsSmTm(i_r30));
    });
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    const i_r30 = ctx.index;
    const ctx_r7 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtwoWayProperty"]("ngModel", ctx_r7.tmModel[i_r30]);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngModelOptions", _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵpureFunction0"](2, _c1));
  }
}
function MonthlyAllocationEntryPgComponent_div_109_th_47_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "th", 118)(1, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](2, "TM");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵattribute"]("rowspan", 2);
  }
}
function MonthlyAllocationEntryPgComponent_div_109_th_49_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](0, "th", 110);
  }
  if (rf & 2) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngStyle", _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵpureFunction0"](1, _c6));
  }
}
function MonthlyAllocationEntryPgComponent_div_109_td_50_Template(rf, ctx) {
  if (rf & 1) {
    const _r31 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵgetCurrentView"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "td", 116)(1, "input", 117);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtwoWayListener"]("ngModelChange", function MonthlyAllocationEntryPgComponent_div_109_td_50_Template_input_ngModelChange_1_listener($event) {
      const i_r32 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵrestoreView"](_r31).index;
      const ctx_r7 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"](2);
      _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtwoWayBindingSet"](ctx_r7.smModel[i_r32], $event) || (ctx_r7.smModel[i_r32] = $event);
      return _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵresetView"]($event);
    });
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵlistener"]("change", function MonthlyAllocationEntryPgComponent_div_109_td_50_Template_input_change_1_listener() {
      const i_r32 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵrestoreView"](_r31).index;
      const ctx_r7 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"](2);
      return _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵresetView"](ctx_r7.claculateFieldsSmTm(i_r32));
    });
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    const i_r32 = ctx.index;
    const ctx_r7 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtwoWayProperty"]("ngModel", ctx_r7.smModel[i_r32]);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngModelOptions", _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵpureFunction0"](2, _c1));
  }
}
function MonthlyAllocationEntryPgComponent_div_109_th_52_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "th", 118)(1, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](2, "SM");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵattribute"]("rowspan", 2);
  }
}
function MonthlyAllocationEntryPgComponent_div_109_th_54_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](0, "th", 110);
  }
  if (rf & 2) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngStyle", _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵpureFunction0"](1, _c6));
  }
}
function MonthlyAllocationEntryPgComponent_div_109_td_55_Template(rf, ctx) {
  if (rf & 1) {
    const _r33 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵgetCurrentView"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "td", 116)(1, "input", 119);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtwoWayListener"]("ngModelChange", function MonthlyAllocationEntryPgComponent_div_109_td_55_Template_input_ngModelChange_1_listener($event) {
      const i_r34 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵrestoreView"](_r33).index;
      const ctx_r7 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"](2);
      _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtwoWayBindingSet"](ctx_r7.totalAllocationModel[i_r34], $event) || (ctx_r7.totalAllocationModel[i_r34] = $event);
      return _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵresetView"]($event);
    });
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵlistener"]("change", function MonthlyAllocationEntryPgComponent_div_109_td_55_Template_input_change_1_listener() {
      const i_r34 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵrestoreView"](_r33).index;
      const ctx_r7 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"](2);
      return _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵresetView"](ctx_r7.claculateFieldsSmTm(i_r34));
    });
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    const i_r34 = ctx.index;
    const ctx_r7 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtwoWayProperty"]("ngModel", ctx_r7.totalAllocationModel[i_r34]);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngModelOptions", _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵpureFunction0"](2, _c1));
  }
}
function MonthlyAllocationEntryPgComponent_div_109_th_57_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "th", 118)(1, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](2, "Total");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵattribute"]("rowspan", 3);
  }
}
function MonthlyAllocationEntryPgComponent_div_109_th_59_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](0, "th", 115);
  }
  if (rf & 2) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngStyle", _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵpureFunction0"](1, _c6));
  }
}
function MonthlyAllocationEntryPgComponent_div_109_td_60_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "td", 114)(1, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    const row_r35 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](row_r35.stock);
  }
}
function MonthlyAllocationEntryPgComponent_div_109_th_62_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "th", 112)(1, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](2, "Total Stock");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵattribute"]("rowspan", 3);
  }
}
function MonthlyAllocationEntryPgComponent_div_109_th_64_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](0, "th", 115);
  }
  if (rf & 2) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngStyle", _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵpureFunction0"](1, _c6));
  }
}
function MonthlyAllocationEntryPgComponent_div_109_td_65_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "td", 114)(1, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    const row_r36 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](row_r36.intransit);
  }
}
function MonthlyAllocationEntryPgComponent_div_109_th_67_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "th", 112)(1, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](2, "Transit Stock");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵattribute"]("rowspan", 3);
  }
}
function MonthlyAllocationEntryPgComponent_div_109_th_69_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](0, "th", 115);
  }
  if (rf & 2) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngStyle", _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵpureFunction0"](1, _c6));
  }
}
function MonthlyAllocationEntryPgComponent_div_109_td_70_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "td", 114)(1, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    const row_r37 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](row_r37.so_far_qty);
  }
}
function MonthlyAllocationEntryPgComponent_div_109_th_72_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "th", 112)(1, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](2, "Pending Alloc.");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵattribute"]("rowspan", 3);
  }
}
function MonthlyAllocationEntryPgComponent_div_109_th_74_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](0, "th", 110);
  }
  if (rf & 2) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngStyle", _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵpureFunction0"](1, _c6));
  }
}
function MonthlyAllocationEntryPgComponent_div_109_td_75_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "td", 120)(1, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    const i_r38 = ctx.index;
    const ctx_r7 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngClass", _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵpureFunction1"](2, _c7, ctx_r7.balanceStockModel[i_r38] < 0));
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](ctx_r7.balanceStockModel[i_r38]);
  }
}
function MonthlyAllocationEntryPgComponent_div_109_th_77_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "th", 112)(1, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](2, "+/- Stock");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵattribute"]("rowspan", 3);
  }
}
function MonthlyAllocationEntryPgComponent_div_109_th_79_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "th", 118)(1, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](2, "Product Details");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵattribute"]("colspan", 5);
  }
}
function MonthlyAllocationEntryPgComponent_div_109_th_81_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "th", 112)(1, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](2, "Allocation");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵattribute"]("colspan", 6);
  }
}
function MonthlyAllocationEntryPgComponent_div_109_th_83_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "th", 118)(1, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](2, "Stock");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵattribute"]("colspan", 4);
  }
}
function MonthlyAllocationEntryPgComponent_div_109_th_85_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "th", 118)(1, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    const ctx_r7 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](ctx_r7.teCount);
  }
}
function MonthlyAllocationEntryPgComponent_div_109_th_87_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "th", 118)(1, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    const ctx_r7 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](ctx_r7.dmCount);
  }
}
function MonthlyAllocationEntryPgComponent_div_109_th_89_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "th", 118)(1, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    const ctx_r7 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](ctx_r7.rmbCount);
  }
}
function MonthlyAllocationEntryPgComponent_div_109_th_91_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "th", 118)(1, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    const ctx_r7 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](ctx_r7.tmCount);
  }
}
function MonthlyAllocationEntryPgComponent_div_109_th_93_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "th", 118)(1, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    const ctx_r7 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](ctx_r7.smCount);
  }
}
function MonthlyAllocationEntryPgComponent_div_109_th_95_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "th", 118)(1, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](2, "Nos");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
  }
}
function MonthlyAllocationEntryPgComponent_div_109_th_97_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "th", 118)(1, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](2, "Nos");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
  }
}
function MonthlyAllocationEntryPgComponent_div_109_th_99_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "th", 118)(1, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](2, "Nos");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
  }
}
function MonthlyAllocationEntryPgComponent_div_109_th_101_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "th", 118)(1, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](2, "Nos");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
  }
}
function MonthlyAllocationEntryPgComponent_div_109_th_103_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "th", 118)(1, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](2, "Nos");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
  }
}
function MonthlyAllocationEntryPgComponent_div_109_th_105_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "th", 118)(1, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](2, "Nos");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
  }
}
function MonthlyAllocationEntryPgComponent_div_109_th_107_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "th", 112)(1, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](2, "Nos");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
  }
}
function MonthlyAllocationEntryPgComponent_div_109_th_109_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "th", 112)(1, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](2, "Nos ");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
  }
}
function MonthlyAllocationEntryPgComponent_div_109_th_111_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "th", 112)(1, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](2, "Nos ");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
  }
}
function MonthlyAllocationEntryPgComponent_div_109_th_113_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "th", 112)(1, "b");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](2, "Nos ");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
  }
}
function MonthlyAllocationEntryPgComponent_div_109_tr_114_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](0, "tr", 121);
  }
}
function MonthlyAllocationEntryPgComponent_div_109_tr_115_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](0, "tr", 121);
  }
}
function MonthlyAllocationEntryPgComponent_div_109_tr_116_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](0, "tr", 122);
  }
}
function MonthlyAllocationEntryPgComponent_div_109_tr_117_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](0, "tr", 121);
  }
}
function MonthlyAllocationEntryPgComponent_div_109_tr_118_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](0, "tr", 121);
  }
}
function MonthlyAllocationEntryPgComponent_div_109_tr_119_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](0, "tr", 123);
  }
}
function MonthlyAllocationEntryPgComponent_div_109_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "div", 5)(1, "div", 48)(2, "table", 49);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](3, 50);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](4, MonthlyAllocationEntryPgComponent_div_109_th_4_Template, 1, 2, "th", 51)(5, MonthlyAllocationEntryPgComponent_div_109_td_5_Template, 2, 1, "td", 52);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](6, 53);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](7, MonthlyAllocationEntryPgComponent_div_109_th_7_Template, 3, 1, "th", 54);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](8, 55);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](9, MonthlyAllocationEntryPgComponent_div_109_th_9_Template, 1, 2, "th", 51)(10, MonthlyAllocationEntryPgComponent_div_109_td_10_Template, 2, 1, "td", 52);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](11, 56);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](12, MonthlyAllocationEntryPgComponent_div_109_th_12_Template, 3, 1, "th", 54);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](13, 57);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](14, MonthlyAllocationEntryPgComponent_div_109_th_14_Template, 1, 2, "th", 51)(15, MonthlyAllocationEntryPgComponent_div_109_td_15_Template, 3, 6, "td", 58);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](16, 59);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](17, MonthlyAllocationEntryPgComponent_div_109_th_17_Template, 3, 1, "th", 54);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](18, 60);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](19, MonthlyAllocationEntryPgComponent_div_109_th_19_Template, 1, 2, "th", 51)(20, MonthlyAllocationEntryPgComponent_div_109_td_20_Template, 2, 1, "td", 61);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](21, 62);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](22, MonthlyAllocationEntryPgComponent_div_109_th_22_Template, 3, 1, "th", 54);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](23, 63);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](24, MonthlyAllocationEntryPgComponent_div_109_th_24_Template, 1, 2, "th", 51)(25, MonthlyAllocationEntryPgComponent_div_109_td_25_Template, 2, 1, "td", 61);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](26, 64);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](27, MonthlyAllocationEntryPgComponent_div_109_th_27_Template, 3, 1, "th", 54);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](28, 65);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](29, MonthlyAllocationEntryPgComponent_div_109_th_29_Template, 1, 2, "th", 66)(30, MonthlyAllocationEntryPgComponent_div_109_td_30_Template, 2, 3, "td", 67);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](31, 68);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](32, MonthlyAllocationEntryPgComponent_div_109_th_32_Template, 3, 1, "th", 69);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](33, 70);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](34, MonthlyAllocationEntryPgComponent_div_109_th_34_Template, 1, 2, "th", 66)(35, MonthlyAllocationEntryPgComponent_div_109_td_35_Template, 2, 3, "td", 67);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](36, 71);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](37, MonthlyAllocationEntryPgComponent_div_109_th_37_Template, 3, 1, "th", 69);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](38, 72);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](39, MonthlyAllocationEntryPgComponent_div_109_th_39_Template, 1, 2, "th", 51)(40, MonthlyAllocationEntryPgComponent_div_109_td_40_Template, 2, 3, "td", 67);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](41, 73);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](42, MonthlyAllocationEntryPgComponent_div_109_th_42_Template, 3, 1, "th", 69);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](43, 74);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](44, MonthlyAllocationEntryPgComponent_div_109_th_44_Template, 1, 2, "th", 51)(45, MonthlyAllocationEntryPgComponent_div_109_td_45_Template, 2, 3, "td", 67);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](46, 75);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](47, MonthlyAllocationEntryPgComponent_div_109_th_47_Template, 3, 1, "th", 69);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](48, 76);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](49, MonthlyAllocationEntryPgComponent_div_109_th_49_Template, 1, 2, "th", 51)(50, MonthlyAllocationEntryPgComponent_div_109_td_50_Template, 2, 3, "td", 67);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](51, 77);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](52, MonthlyAllocationEntryPgComponent_div_109_th_52_Template, 3, 1, "th", 69);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](53, 78);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](54, MonthlyAllocationEntryPgComponent_div_109_th_54_Template, 1, 2, "th", 51)(55, MonthlyAllocationEntryPgComponent_div_109_td_55_Template, 2, 3, "td", 67);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](56, 79);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](57, MonthlyAllocationEntryPgComponent_div_109_th_57_Template, 3, 1, "th", 69);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](58, 80);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](59, MonthlyAllocationEntryPgComponent_div_109_th_59_Template, 1, 2, "th", 66)(60, MonthlyAllocationEntryPgComponent_div_109_td_60_Template, 3, 1, "td", 61);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](61, 81);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](62, MonthlyAllocationEntryPgComponent_div_109_th_62_Template, 3, 1, "th", 54);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](63, 82);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](64, MonthlyAllocationEntryPgComponent_div_109_th_64_Template, 1, 2, "th", 66)(65, MonthlyAllocationEntryPgComponent_div_109_td_65_Template, 3, 1, "td", 61);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](66, 83);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](67, MonthlyAllocationEntryPgComponent_div_109_th_67_Template, 3, 1, "th", 54);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](68, 84);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](69, MonthlyAllocationEntryPgComponent_div_109_th_69_Template, 1, 2, "th", 66)(70, MonthlyAllocationEntryPgComponent_div_109_td_70_Template, 3, 1, "td", 61);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](71, 85);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](72, MonthlyAllocationEntryPgComponent_div_109_th_72_Template, 3, 1, "th", 54);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](73, 86);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](74, MonthlyAllocationEntryPgComponent_div_109_th_74_Template, 1, 2, "th", 51)(75, MonthlyAllocationEntryPgComponent_div_109_td_75_Template, 3, 4, "td", 87);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](76, 88);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](77, MonthlyAllocationEntryPgComponent_div_109_th_77_Template, 3, 1, "th", 54);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](78, 89);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](79, MonthlyAllocationEntryPgComponent_div_109_th_79_Template, 3, 1, "th", 69);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](80, 90);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](81, MonthlyAllocationEntryPgComponent_div_109_th_81_Template, 3, 1, "th", 54);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](82, 91);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](83, MonthlyAllocationEntryPgComponent_div_109_th_83_Template, 3, 1, "th", 69);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](84, 92);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](85, MonthlyAllocationEntryPgComponent_div_109_th_85_Template, 3, 1, "th", 69);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](86, 93);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](87, MonthlyAllocationEntryPgComponent_div_109_th_87_Template, 3, 1, "th", 69);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](88, 94);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](89, MonthlyAllocationEntryPgComponent_div_109_th_89_Template, 3, 1, "th", 69);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](90, 95);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](91, MonthlyAllocationEntryPgComponent_div_109_th_91_Template, 3, 1, "th", 69);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](92, 96);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](93, MonthlyAllocationEntryPgComponent_div_109_th_93_Template, 3, 1, "th", 69);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](94, 97);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](95, MonthlyAllocationEntryPgComponent_div_109_th_95_Template, 3, 0, "th", 69);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](96, 98);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](97, MonthlyAllocationEntryPgComponent_div_109_th_97_Template, 3, 0, "th", 69);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](98, 99);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](99, MonthlyAllocationEntryPgComponent_div_109_th_99_Template, 3, 0, "th", 69);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](100, 100);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](101, MonthlyAllocationEntryPgComponent_div_109_th_101_Template, 3, 0, "th", 69);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](102, 101);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](103, MonthlyAllocationEntryPgComponent_div_109_th_103_Template, 3, 0, "th", 69);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](104, 102);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](105, MonthlyAllocationEntryPgComponent_div_109_th_105_Template, 3, 0, "th", 69);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](106, 103);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](107, MonthlyAllocationEntryPgComponent_div_109_th_107_Template, 3, 0, "th", 54);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](108, 104);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](109, MonthlyAllocationEntryPgComponent_div_109_th_109_Template, 3, 0, "th", 54);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](110, 105);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](111, MonthlyAllocationEntryPgComponent_div_109_th_111_Template, 3, 0, "th", 54);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](112, 106);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](113, MonthlyAllocationEntryPgComponent_div_109_th_113_Template, 3, 0, "th", 54);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](114, MonthlyAllocationEntryPgComponent_div_109_tr_114_Template, 1, 0, "tr", 107)(115, MonthlyAllocationEntryPgComponent_div_109_tr_115_Template, 1, 0, "tr", 107)(116, MonthlyAllocationEntryPgComponent_div_109_tr_116_Template, 1, 0, "tr", 108)(117, MonthlyAllocationEntryPgComponent_div_109_tr_117_Template, 1, 0, "tr", 107)(118, MonthlyAllocationEntryPgComponent_div_109_tr_118_Template, 1, 0, "tr", 107)(119, MonthlyAllocationEntryPgComponent_div_109_tr_119_Template, 1, 0, "tr", 109);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()()();
  }
  if (rf & 2) {
    const ctx_r7 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("dataSource", ctx_r7.productList);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](112);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("matHeaderRowDef", _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵpureFunction0"](12, _c2))("matHeaderRowDefSticky", true);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("matHeaderRowDef", _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵpureFunction0"](13, _c3))("matHeaderRowDefSticky", true);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("matHeaderRowDef", ctx_r7.displayedColumns)("matHeaderRowDefSticky", true);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("matHeaderRowDef", _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵpureFunction0"](14, _c4))("matHeaderRowDefSticky", true);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("matHeaderRowDef", _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵpureFunction0"](15, _c5))("matHeaderRowDefSticky", true);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("matRowDefColumns", ctx_r7.displayedColumns);
  }
}
function AllocEntModifyComponentpg_mat_option_13_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "mat-option", 29);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const m_r1 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("value", _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵpureFunction2"](2, _c0, m_r1.sa_group_id, m_r1.sa_group_name));
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](m_r1.sa_group_name);
  }
}
function AllocEntModifyComponentpg_th_18_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "th", 30);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1, "Allocation By");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
}
function AllocEntModifyComponentpg_td_19_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "td", 31);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const row_r2 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](row_r2.allocationByName);
  }
}
function AllocEntModifyComponentpg_th_21_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "th", 30);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1, "Brand");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
}
function AllocEntModifyComponentpg_td_22_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "td", 31);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const row_r3 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](row_r3.sa_group_name);
  }
}
function AllocEntModifyComponentpg_th_24_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "th", 30);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1, "Product Description");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
}
function AllocEntModifyComponentpg_td_25_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "td", 32);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵpipe"](2, "slice");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const row_r4 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵpropertyInterpolate"]("matTooltip", row_r4.smp_prod_name);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](_angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵpipeBind3"](2, 2, row_r4.smp_prod_name, 0, 20));
  }
}
function AllocEntModifyComponentpg_th_27_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "th", 30);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1, "TE");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
}
function AllocEntModifyComponentpg_td_28_Template(rf, ctx) {
  if (rf & 1) {
    const _r5 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵgetCurrentView"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "td", 33)(1, "input", 34);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtwoWayListener"]("ngModelChange", function AllocEntModifyComponentpg_td_28_Template_input_ngModelChange_1_listener($event) {
      const row_r6 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵrestoreView"](_r5).$implicit;
      const ctx_r6 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"]();
      _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtwoWayBindingSet"](ctx_r6.data.teModel[row_r6.index], $event) || (ctx_r6.data.teModel[row_r6.index] = $event);
      return _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵresetView"]($event);
    });
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    const row_r6 = ctx.$implicit;
    const ctx_r6 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtwoWayProperty"]("ngModel", ctx_r6.data.teModel[row_r6.index]);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngModelOptions", _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵpureFunction0"](2, _c1));
  }
}
function AllocEntModifyComponentpg_th_30_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "th", 30);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1, "DM");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
}
function AllocEntModifyComponentpg_td_31_Template(rf, ctx) {
  if (rf & 1) {
    const _r8 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵgetCurrentView"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "td", 33)(1, "input", 34);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtwoWayListener"]("ngModelChange", function AllocEntModifyComponentpg_td_31_Template_input_ngModelChange_1_listener($event) {
      const row_r9 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵrestoreView"](_r8).$implicit;
      const ctx_r6 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"]();
      _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtwoWayBindingSet"](ctx_r6.data.dmModel[row_r9.index], $event) || (ctx_r6.data.dmModel[row_r9.index] = $event);
      return _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵresetView"]($event);
    });
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    const row_r9 = ctx.$implicit;
    const ctx_r6 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtwoWayProperty"]("ngModel", ctx_r6.data.dmModel[row_r9.index]);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngModelOptions", _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵpureFunction0"](2, _c1));
  }
}
function AllocEntModifyComponentpg_th_33_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "th", 30);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1, "RBM");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
}
function AllocEntModifyComponentpg_td_34_Template(rf, ctx) {
  if (rf & 1) {
    const _r10 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵgetCurrentView"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "td", 33)(1, "input", 34);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtwoWayListener"]("ngModelChange", function AllocEntModifyComponentpg_td_34_Template_input_ngModelChange_1_listener($event) {
      const row_r11 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵrestoreView"](_r10).$implicit;
      const ctx_r6 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"]();
      _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtwoWayBindingSet"](ctx_r6.data.rbmModel[row_r11.index], $event) || (ctx_r6.data.rbmModel[row_r11.index] = $event);
      return _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵresetView"]($event);
    });
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    const row_r11 = ctx.$implicit;
    const ctx_r6 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtwoWayProperty"]("ngModel", ctx_r6.data.rbmModel[row_r11.index]);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngModelOptions", _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵpureFunction0"](2, _c1));
  }
}
function AllocEntModifyComponentpg_th_37_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "th", 30);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1, "TM");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
}
function AllocEntModifyComponentpg_td_38_Template(rf, ctx) {
  if (rf & 1) {
    const _r12 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵgetCurrentView"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "td", 33)(1, "input", 34);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtwoWayListener"]("ngModelChange", function AllocEntModifyComponentpg_td_38_Template_input_ngModelChange_1_listener($event) {
      const row_r13 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵrestoreView"](_r12).$implicit;
      const ctx_r6 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"]();
      _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtwoWayBindingSet"](ctx_r6.data.tmModel[row_r13.index], $event) || (ctx_r6.data.tmModel[row_r13.index] = $event);
      return _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵresetView"]($event);
    });
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    const row_r13 = ctx.$implicit;
    const ctx_r6 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtwoWayProperty"]("ngModel", ctx_r6.data.tmModel[row_r13.index]);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngModelOptions", _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵpureFunction0"](2, _c1));
  }
}
function AllocEntModifyComponentpg_th_40_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "th", 30);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1, "SM");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
}
function AllocEntModifyComponentpg_td_41_Template(rf, ctx) {
  if (rf & 1) {
    const _r14 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵgetCurrentView"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "td", 33)(1, "input", 34);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtwoWayListener"]("ngModelChange", function AllocEntModifyComponentpg_td_41_Template_input_ngModelChange_1_listener($event) {
      const row_r15 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵrestoreView"](_r14).$implicit;
      const ctx_r6 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"]();
      _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtwoWayBindingSet"](ctx_r6.data.smModel[row_r15.index], $event) || (ctx_r6.data.smModel[row_r15.index] = $event);
      return _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵresetView"]($event);
    });
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    const row_r15 = ctx.$implicit;
    const ctx_r6 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtwoWayProperty"]("ngModel", ctx_r6.data.smModel[row_r15.index]);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngModelOptions", _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵpureFunction0"](2, _c1));
  }
}
function AllocEntModifyComponentpg_tr_42_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](0, "tr", 35);
  }
}
function AllocEntModifyComponentpg_tr_43_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](0, "tr", 36);
  }
}
const _c8 = "/*# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IiIsInNvdXJjZVJvb3QiOiIifQ== */";
function FieldstaffDetailsComponentpg_tr_15_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "tr")(1, "td", 4);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](3, "td", 4);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](4);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](5, "td", 4);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](6);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](7, "td", 4);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](8);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
  }
  if (rf & 2) {
    const row_r1 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate1"]("", row_r1.fstaff_code, " ");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate1"]("", row_r1.fstaff_name, " ");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate1"]("", row_r1.fstaff_terrname, " ");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](2);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate1"]("", row_r1.company, " ");
  }
}
function AllocRegenerateComponentpg_th_8_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "th", 18);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1, "Select");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
}
function AllocRegenerateComponentpg_td_9_Template(rf, ctx) {
  if (rf & 1) {
    const _r1 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵgetCurrentView"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "td", 19)(1, "mat-radio-button", 20);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵlistener"]("change", function AllocRegenerateComponentpg_td_9_Template_mat_radio_button_change_1_listener() {
      const row_r2 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵrestoreView"](_r1).$implicit;
      const ctx_r2 = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵnextContext"]();
      return _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵresetView"](ctx_r2.onSelect(row_r2));
    });
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
  }
}
function AllocRegenerateComponentpg_th_11_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "th", 18);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1, "Date Of Allocation");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
}
function AllocRegenerateComponentpg_td_12_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "td", 19);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const row_r4 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](row_r4.allocationDate);
  }
}
function AllocRegenerateComponentpg_th_14_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "th", 18);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1, "Division");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
}
function AllocRegenerateComponentpg_td_15_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "td", 21);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const row_r5 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](row_r5.div_disp_nm);
  }
}
function AllocRegenerateComponentpg_th_17_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "th", 18);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1, "Doc Number");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
}
function AllocRegenerateComponentpg_td_18_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "td", 19);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const row_r6 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](row_r6.allocDocNo);
  }
}
function AllocRegenerateComponentpg_th_20_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "th", 18);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1, "Company");
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
}
function AllocRegenerateComponentpg_td_21_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "td", 19);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1);
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
  }
  if (rf & 2) {
    const row_r7 = ctx.$implicit;
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](row_r7.companyName);
  }
}
function AllocRegenerateComponentpg_tr_22_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](0, "tr", 22);
  }
}
function AllocRegenerateComponentpg_tr_23_Template(rf, ctx) {
  if (rf & 1) {
    _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelement"](0, "tr", 23);
  }
}
let MonthlyAllocationEntryPgComponent = /*#__PURE__*/(() => {
  class MonthlyAllocationEntryPgComponent {
    fb;
    medicoUtility;
    medicoService;
    allocationService;
    dialog;
    constants = new src_app_classes_constants__WEBPACK_IMPORTED_MODULE_0__.Constants();
    session;
    allocationDate;
    enterFor;
    disableEnterFor = false;
    teams;
    subTeamList;
    brands;
    frequency = new Array();
    allocation_for = new Array();
    copyAllocation = new Array();
    fieldOptions = new Array();
    productList;
    allocEntList;
    planType = new Array();
    includeExclude = new Array();
    planTypeDesc;
    staffCount;
    teamSize;
    teamId;
    teamReqInd;
    productDetails = new Array();
    monthOfUse = new Array();
    allocationMode = new Array();
    allocationBy = new Array();
    showStaffCount = false;
    showHeader = true;
    showSmTm = false;
    headerFreezed = false;
    subTeamFreezed = true;
    frequencyFreezed = false;
    disableForMonthly = false;
    disableForMonthly2 = false;
    disableForAdditional = false;
    allocationMonth;
    allocationMonthPeriodCode;
    allocationSaveMode;
    allocationId;
    allocationCycle;
    allocatioinbProductType;
    allocationByName;
    rmbCount;
    dmCount;
    teCount;
    smCount;
    tmCount;
    tmqty = 0;
    smqty = 0;
    teModel = new Array();
    dmModel = new Array();
    rmbModel = new Array();
    tmModel = new Array();
    smModel = new Array();
    productIds = new Array();
    brandIds = new Array();
    totalAllocationModel = new Array();
    balanceStockModel = new Array();
    totalStock = new Array();
    allocationSoFar = new Array();
    finYearId;
    exludeDiscProd;
    zeroStockInd;
    coreTeamId;
    monthOfUsePeriodCode;
    // monthOfUsePeriodName:string;
    assistantInd;
    managerList = new Array();
    loginId;
    loginEmpId;
    mgrEmpId;
    mgr_id;
    managerDetails = new Array();
    allocDocNo;
    managerIndicator = true;
    isAssistant = true;
    isPrivious;
    agEntDetails = new Array();
    enteredBrandsEnt = new Array();
    teModelUpdate = new Array();
    dmModelUpdate = new Array();
    rmbModelUpdate = new Array();
    tmModelUpdate = new Array();
    smModelUpdate = new Array();
    productIdsUpdate = new Array();
    alloc_gen_ent_ids = new Array();
    disable = false;
    enteredBrandId = new Array();
    selectedBrands = new Array();
    dateArray = new Array();
    maxDate;
    showReport = false;
    data;
    checkedInd = false;
    selectAlls = false;
    ids = new Array();
    headerIds = new Array();
    generatedAlloc;
    financialYear;
    paginator;
    displayedColumns = ["productCode", "brand", "prodDescription", "pack", "minAllocationQty", "te", "dm", "rbm", "tm", "sm", "totatlAllocationQty", "totalStock", "transitStock", "allocationSoFar", "balanceStock"];
    displayedColumns1 = ["productCode", "brand", "prodDescription", "pack", "te", "dm", "rbm", "tm", "sm"];
    reportList;
    columns = [{
      name: 'position',
      label: 'No.'
    }, {
      name: 'name',
      label: 'Name'
    }, {
      name: 'weight',
      label: 'Weight'
    }, {
      name: 'symbol',
      label: 'Symbol'
    }];
    displayedColumnsdemo = this.columns.map(column => column.name);
    form;
    constructor(fb, medicoUtility, medicoService, allocationService, dialog) {
      this.fb = fb;
      this.medicoUtility = medicoUtility;
      this.medicoService = medicoService;
      this.allocationService = allocationService;
      this.dialog = dialog;
      this.form = this.fb.group({
        managerDetails: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        mgr_id: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        mgrEmpId: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        teamId: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        sub_team_code: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        brandId: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        planType: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        frequency: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl("", _angular_forms__WEBPACK_IMPORTED_MODULE_6__.Validators.required),
        alloc_type: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl("", _angular_forms__WEBPACK_IMPORTED_MODULE_6__.Validators.required),
        copyAllocationFrom: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        productIds: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        brandIds: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        allocationMode: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl("", _angular_forms__WEBPACK_IMPORTED_MODULE_6__.Validators.required),
        selectedBrandId: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        allocationById: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl("", _angular_forms__WEBPACK_IMPORTED_MODULE_6__.Validators.required),
        zeroStockInd: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        teModel: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        dmModel: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        rmbModel: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        tmModel: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        smModel: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        allocationSaveMode: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        allocationId: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        allocationCycle: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        allocationMonthPeriodCode: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        fieldtype: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        allocationMonth: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        planTypeDesc: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        finYearId: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        exludeDiscProd: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        empId: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        coreTeamId: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        tmqty: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        smqty: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        monthOfUsePeriodCode: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        //monthOfUsePeriodName:new FormControl(""),
        alloc_gen_ent_ids: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl(""),
        user_role: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl("")
      });
      this.medicoService.headingName = "";
      this.session = this.medicoUtility.getSessionVariables();
      this.getTeamAndPlans();
      this.frequency = [{
        key: "M",
        value: "Monthly"
      }, {
        key: "A",
        value: "Additional"
      }];
      this.allocation_for = [{
        key: "F",
        value: "Fieldstaff"
      }, {
        key: "R",
        value: "Doctor"
      }];
      this.allocationMode = [
      // { key: "6", value: "All"},
      {
        key: "0",
        value: "By CFA Destination"
      }, {
        key: "1",
        value: "By Zone"
      }, {
        key: "2",
        value: "By State"
      }, {
        key: "3",
        value: "By Region"
      }, {
        key: "4",
        value: "By District"
      }, {
        key: "5",
        value: "Self"
      }];
      this.fieldOptions = [{
        key: "T",
        value: "Yes"
      }, {
        key: "F",
        value: "No"
      }];
      this.includeExclude = [{
        key: "N",
        value: "Include"
      }, {
        key: "Y",
        value: "Exclude"
      }];
      this.form.get("fieldtype")?.setValue("F");
      this.form.get("exludeDiscProd")?.setValue("Y");
      this.form.get("zeroStockInd")?.setValue("Y");
      this.form.get("sub_team_code")?.setValue("0");
      this.form.get("alloc_type")?.setValue("F");
      this.teCount = 0;
      this.dmCount = 0;
      this.rmbCount = 0;
      this.smCount = 0;
      this.tmCount = 0;
      this.mgrEmpId = "0";
      this.form.get("coreTeamId")?.setValue("A");
      // this.monthOfUsePeriodName=this.allocationMonthPeriodCode;
      this.form.get("user_role")?.setValue(this.session.USER_ROLE);
      if (this.session.USER_ROLE == this.constants.ROLE_WAREH) {
        this.form.get("frequency")?.setValue("A");
        this.frequencyFreezed = true;
        this.disableForAdditional = true;
      }
      this.financialYear = this.session.FINANCIAL_YEAR;
      this.unlockProducts();
    }
    ngOnInit() {}
    getTeamAndPlans() {
      this.medicoUtility.toggleProgressBar(true);
      this.allocationService.getTeamPlansForMonthly(this.session.EMP_ID).subscribe(response => {
        this.allocationDate = response.allocationDate;
        this.teams = response.team;
        this.planType = response.plans;
        this.allocationMonthPeriodCode = response.allocationMonthPeriodCode;
        this.allocationMonth = response.allocationMonth;
        this.finYearId = response.finYearId;
        this.copyAllocation = response.periodList;
        this.medicoService.headingName = "";
        this.allocationSaveMode = "E";
        this.allocationId = 0;
        this.allocationCycle = "1";
        this.monthOfUse = response.monthOfUse;
        this.loginId = this.session.LD_ID;
        this.loginEmpId = this.session.EMP_ID;
        this.assistantInd = this.session.LD_EXEC_ASST_IND;
        this.allocDocNo = "";
        this.form.get("monthOfUsePeriodCode")?.setValue(this.allocationMonthPeriodCode);
        if (this.assistantInd == 'Y') {
          this.managerList = response.managerList;
          this.enterFor = "Enter for";
          this.managerIndicator = false;
        } else {
          this.managerList = [{
            mgr_id: this.loginId,
            mgrEmpId: this.loginEmpId,
            mgr_name: "Self"
          }];
          this.mgrEmpId = this.loginEmpId;
          this.mgr_id = this.loginId;
          this.enterFor = this.session.FNAME + " " + this.session.LNAME; //for UI
          this.disableEnterFor = true;
          this.isAssistant = false;
        }
        this.medicoUtility.toggleProgressBar(false);
      });
    }
    setTeam() {
      this.medicoUtility.toggleProgressBar(true);
      if (this.assistantInd == 'Y') {
        this.allocationService.getTeamForManager(this.mgrEmpId).subscribe(response => {
          this.teams = response.team;
          this.form.get("planType")?.reset();
          this.brands = new Array();
          this.medicoUtility.toggleProgressBar(false);
        });
      }
    }
    checkAllocHeaderExist() {
      this.medicoUtility.toggleProgressBar(true);
      this.form.get("planType")?.reset();
      this.form.get("selectedBrandId")?.reset();
      this.teamId = this.form.get("teamId")?.value;
      this.teamReqInd = this.teams.filter(x => x.div_id == this.teamId).map(x => x.team_reqd_ind)[0];
      if (this.teamReqInd == 'Y') {
        this.subTeamFreezed = false;
      } else {
        this.form.get("sub_team_code")?.setValue(0);
        this.subTeamFreezed = true;
      }
      this.productList = new _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatTableDataSource();
      this.allocationService.checkAllocHeaderExist(this.mgrEmpId, this.teamId, this.allocationMonth, this.finYearId, this.form.get("frequency")?.value, this.teamReqInd).subscribe(response => {
        if (response.allowAllocation == true) {
          this.disable = true;
          if (response.fieldstaffList != null && response.fieldstaffList != undefined) {
            const dialogRef = this.dialog.open(FieldstaffDetailsComponentpg, {
              width: '1000',
              height: '300',
              data: {
                list: response.fieldstaffList
              }
            });
          }
        } else {
          this.allocationCycle = response.allocationCycle;
          this.subTeamList = response.subTeamList;
          if (response.approvalStatus != undefined && response.approvalStatus == 'G') {
            this.allocationId = response.alloc_gen_id;
            this.allocDocNo = response.alloc_doc_no;
            this.headerFreezed = true;
            this.disableEnterFor = true;
            this.onClickOfView();
          } else {
            this.allocationId = 0;
            this.allocDocNo = "";
          }
          if (this.form.get("frequency")?.value == 'M' && (response.approvalStatus == 'A' || response.approvalStatus == 'F' || response.approvalStatus == 'E')) {
            this.form.get("frequency")?.reset();
            this.medicoService.confirmBox("Message", "Monthly entry option Already completed", "Ok", "");
          }
          if (this.form.get("frequency")?.value == 'M') {
            this.disableForMonthly = true;
            this.disableForMonthly2 = true;
            this.disableForAdditional = false;
            this.form.get("allocationMode")?.setValue(0);
            this.form.get("allocationById")?.setValue(0);
            this.allocationBy = new Array();
            this.setAllocationByCount();
          } else {
            this.disableForAdditional = true;
            this.disableForMonthly = false;
            this.disableForMonthly2 = false;
            this.form.get("allocationMode")?.reset();
            this.form.get("allocationById")?.reset();
          }
          this.disable = false;
          this.monthOfUsePeriodCode = response.monthOfUse;
        }
        this.medicoUtility.toggleProgressBar(false);
      });
    }
    setBrandsAllocationCycle(planTypeDesc) {
      this.medicoUtility.toggleProgressBar(true);
      this.brands = new Array();
      this.productList = new _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatTableDataSource();
      this.form.get("selectedBrandId")?.reset();
      this.form.get("copyAllocationFrom")?.reset();
      this.planTypeDesc = this.form.get("planType")?.value + "_" + planTypeDesc;
      this.teamId = this.form.get("teamId")?.value;
      this.allocationService.setBrandsAllocationCycle(this.mgrEmpId, this.teamId, this.form.get("planType")?.value, this.allocationMonth, this.finYearId, this.form.get("frequency")?.value, this.form.get("sub_team_code")?.value).subscribe(response => {
        this.brands = response.brands;
        this.showStaffCount = true;
        this.setStaffCount();
        this.ids = response.brands.map(x => x.smp_sa_prod_group);
        this.medicoUtility.toggleProgressBar(false);
      });
    }
    setAllocEntList() {
      if (this.agEntDetails != undefined) {
        this.allocEntList = new _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatTableDataSource(this.agEntDetails.slice(0));
        for (let i = 0; i < this.agEntDetails.length; i++) {
          this.teModelUpdate[i] = this.agEntDetails[i].teQty;
          this.dmModelUpdate[i] = this.agEntDetails[i].dmQty;
          this.rmbModelUpdate[i] = this.agEntDetails[i].rmQty;
          this.tmModelUpdate[i] = this.agEntDetails[i].tmQty;
          this.smModelUpdate[i] = this.agEntDetails[i].smQty;
          this.productIdsUpdate[i] = this.agEntDetails[i].productId;
          this.alloc_gen_ent_ids[i] = this.agEntDetails[i].alloc_gen_ent_id;
          this.enteredBrandId[i] = this.agEntDetails[i].sa_group_id;
        }
      }
    }
    setProductList() {
      this.medicoUtility.toggleProgressBar(true);
      this.selectedBrands = this.form.get("selectedBrandId")?.value;
      this.form.get("copyAllocationFrom")?.reset();
      if (this.selectedBrands[this.selectedBrands.length - 1] == 0) {
        this.ids.push(0);
        this.selectAlls = true;
        this.selectedBrands = this.ids;
        this.form.get("selectedBrandId")?.setValue(this.selectedBrands);
      } else if (this.selectAlls == true && !this.selectedBrands.includes(0)) {
        this.selectAlls = false;
        this.selectedBrands = new Array();
        this.form.get("selectedBrandId")?.setValue(this.selectedBrands);
      }
      this.exludeDiscProd = this.form.get("exludeDiscProd")?.value;
      this.zeroStockInd = this.form.get("zeroStockInd")?.value;
      this.allocationService.setProductListForMonthly(this.planTypeDesc, this.selectedBrands, this.exludeDiscProd, this.zeroStockInd, this.teamId, "0", this.allocationMonthPeriodCode, "A", this.finYearId, this.allocationId, "N", this.allocationMonthPeriodCode, this.finYearId, this.form.get("frequency")?.value, this.session.EMP_ID).subscribe(response => {
        // if(response.isBrandEntered==true){
        //   this.medicoService.confirmBox("Message","Duolicate choice for the filter already applied","Ok","");
        //   this.productList = new MatTableDataSource();
        //   this.medicoUtility.toggleProgressBar(false);
        //   return;
        // }
        this.productDetails = response.productList;
        if (this.productDetails != undefined) {
          this.productList = new _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatTableDataSource(this.productDetails.slice(0));
          for (let i = 0; i < this.productDetails.length; i++) {
            if (this.session.USER_ROLE == this.constants.ROLE_WAREH) {
              this.teModel[i] = 0;
            } else {
              this.teModel[i] = this.productDetails[i].plan_qty;
            }
            this.dmModel[i] = 0;
            this.rmbModel[i] = 0;
            this.tmModel[i] = 0;
            this.smModel[i] = 0;
            this.totalAllocationModel[i] = 0;
            this.alloc_gen_ent_ids[i] = 0;
            this.totalStock[i] = this.productDetails[i].stock;
            this.balanceStockModel[i] = this.totalStock[i] - this.teModel[i];
            this.allocationSoFar[i] = this.productDetails[i].so_far_qty;
            this.productIds[i] = this.productDetails[i].smp_prod_id;
            this.brandIds[i] = this.productDetails[i].sa_group_id;
            this.claculateFields(i);
          }
        } else {
          this.productList = new _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatTableDataSource();
        }
        this.medicoUtility.toggleProgressBar(false);
      });
    }
    setStaffCount() {
      this.medicoUtility.toggleProgressBar(true);
      this.allocationService.setStaaffCountForMonthlypg(this.teamId, this.form.get("sub_team_code")?.value, this.form.get("alloc_type")?.value).subscribe(response => {
        this.staffCount = response.staffCount;
        this.showStaffCount = true;
        this.medicoUtility.toggleProgressBar(false);
      });
    }
    setAllocationByList() {
      this.medicoUtility.toggleProgressBar(true);
      if (this.form.get("allocationMode")?.value == 6) {
        this.form.get("allocationById")?.setValue("0");
        this.setAllocationByCount();
      } else {
        this.allocationService.setAllocationByListpg(this.teamId, this.allocationMonthPeriodCode, this.finYearId, this.allocationSaveMode, this.planTypeDesc, this.allocationId, this.allocationCycle, this.allocatioinbProductType, this.form.get("allocationMode")?.value, this.form.get("selectedBrandId")?.value, this.form.get("sub_team_code")?.value, this.form.get("alloc_type")?.value).subscribe(response => {
          this.allocationBy = response.allocationByList;
          this.allocationByName = response.allocationByName;
          this.medicoUtility.toggleProgressBar(false);
        });
      }
    }
    setAllocationByCount() {
      this.medicoUtility.toggleProgressBar(true);
      this.allocationService.setAllocationByCountpg(this.teamId, this.allocationMonthPeriodCode, this.finYearId, this.allocationSaveMode, this.planTypeDesc, this.form.get("allocationById")?.value, this.allocationCycle, this.allocatioinbProductType, this.form.get("allocationMode")?.value, this.form.get("fieldtype")?.value, this.form.get("coreTeamId")?.value, this.form.get("sub_team_code")?.value, this.form.get("alloc_type")?.value).subscribe(response => {
        this.teCount = response.msr;
        this.dmCount = response.abm;
        this.rmbCount = response.rbm;
        if (response.tm != undefined) this.tmCount = response.tm;
        if (response.cm != undefined) this.smCount = response.cm;
        this.productList = new _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatTableDataSource(this.productDetails.slice(0));
        for (let i = 0; i < this.productDetails.length; i++) {
          this.claculateFields(i);
        }
        this.medicoUtility.toggleProgressBar(false);
      });
    }
    resetTable() {
      if (this.productDetails != undefined) {
        for (let i = 0; i < this.productDetails.length; i++) {
          this.teModel[i] = 0;
          this.dmModel[i] = 0;
          this.rmbModel[i] = 0;
          this.tmModel[i] = 0;
          this.smModel[i] = 0;
          this.productIds[i] = this.productDetails[i].smp_prod_id;
          this.totalAllocationModel[i] = 0;
        }
      }
    }
    setEmpDetails() {
      this.managerDetails = this.form.get("managerDetails")?.value;
      this.mgr_id = this.managerDetails[0];
      this.mgrEmpId = this.managerDetails[1];
      this.setTeam();
    }
    showSmTmForEntry() {
      if (this.form.get("fieldtype")?.value == 'T') {
        this.showSmTm = true;
        this.tmqty = 0;
        this.smqty = 0;
      } else {
        this.showSmTm = false;
        this.tmqty = 0;
        this.smqty = 0;
      }
      if (this.productDetails != undefined) {
        for (let i = 0; i < this.productDetails.length; i++) {
          this.claculateFields(i);
        }
      }
    }
    claculateFields(i) {
      this.tmModel[i] = this.tmqty;
      this.smModel[i] = this.smqty;
      //console.log("Total "+this.tmModel[i]+this.tmCount+this.smModel[i]+this.smCount);
      this.totalAllocationModel[i] = this.teModel[i] * this.teCount + this.dmModel[i] * this.dmCount + this.rmbModel[i] * this.rmbCount + this.tmModel[i] * this.tmCount + this.smModel[i] * this.smCount;
      this.balanceStockModel[i] = this.totalStock[i] - this.totalAllocationModel[i] - this.allocationSoFar[i];
    }
    claculateFieldsSmTm(i) {
      //console.log("Total "+this.tmModel[i]+this.tmCount+this.smModel[i]+this.smCount);
      this.totalAllocationModel[i] = this.teModel[i] * this.teCount + this.dmModel[i] * this.dmCount + this.rmbModel[i] * this.rmbCount + this.tmModel[i] * this.tmCount + this.smModel[i] * this.smCount;
      this.balanceStockModel[i] = this.totalStock[i] - this.totalAllocationModel[i] - this.allocationSoFar[i];
    }
    headerHideShow() {
      this.showHeader = false;
    }
    setValuesToSubmit() {
      // this.monthOfUsePeriodCode=this.monthOfUsePeriodName=this.form.get("monthOfUsePeriodCode")?.value;
      this.form.get("teModel")?.setValue(this.teModel);
      this.form.get("dmModel")?.setValue(this.dmModel);
      this.form.get("rmbModel")?.setValue(this.rmbModel);
      this.form.get("tmModel")?.setValue(this.tmModel);
      this.form.get("smModel")?.setValue(this.smModel);
      this.form.get("productIds")?.setValue(this.productIds);
      this.form.get("brandIds")?.setValue(this.brandIds);
      this.form.get("allocationCycle")?.setValue(this.allocationCycle);
      this.form.get("allocationId")?.setValue(this.allocationId);
      this.form.get("allocationMonthPeriodCode")?.setValue(this.allocationMonthPeriodCode);
      this.form.get("allocationMonth")?.setValue(this.allocationMonth);
      this.form.get("teamId")?.setValue(this.teamId);
      this.form.get("finYearId")?.setValue(this.finYearId);
      this.form.get("planTypeDesc")?.setValue(this.planTypeDesc);
      this.form.get("empId")?.setValue(this.session.EMP_ID);
      this.form.get("tmqty")?.setValue(this.tmqty);
      this.form.get("smqty")?.setValue(this.smqty);
      this.form.get("monthOfUsePeriodCode")?.setValue(this.monthOfUsePeriodCode);
      //this.form.get("monthOfUsePeriodName")?.setValue(this.monthOfUsePeriodName);
      this.form.get("mgrEmpId")?.setValue(this.mgrEmpId);
      this.form.get("mgr_id")?.setValue(this.mgr_id);
      this.form.get("alloc_gen_ent_ids")?.setValue(this.alloc_gen_ent_ids);
    }
    saveMonthlyAllocation(saveMode) {
      if (this.form.valid || saveMode == 'S') {
        if (saveMode == 'S' && this.allocationId == 0) {
          this.medicoService.confirmBox("Message", "Complete all entries and then use the Generate option", "Ok", "");
          return;
        }
        if (saveMode == 'E' && this.productDetails == null || saveMode == 'E' && this.productDetails == undefined || saveMode == 'E' && this.productDetails.length == 0) {
          this.medicoService.confirmBox("Message", "Enter selections and save as draft", "Ok", "");
          return;
        }
        this.medicoUtility.toggleProgressBar(true);
        this.setValuesToSubmit();
        this.form.get("allocationSaveMode")?.setValue(saveMode);
        this.allocationService.saveMonthlyAllocation(this.form).subscribe(response => {
          this.medicoUtility.toggleProgressBar(true);
          if (response.DATA_SAVED) {
            this.medicoService.popup(response.RESPONSE_MESSAGE, response.allocDocNo);
            this.allocationId = response.allo_gen_id;
            this.allocDocNo = response.allocDocNo;
            this.headerFreezed = true;
            this.frequencyFreezed = true;
            this.disable = true;
            this.disableForMonthly = true;
            this.disableEnterFor = true;
            this.subTeamFreezed = true;
            //this.resetTable();
            //For Additional Reset
            if (this.form.get("frequency")?.value == 'A') {
              this.form.get("allocationById")?.reset();
              this.setAllocationByList();
            } else {
              this.productList = new _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatTableDataSource();
              this.form.get("allocationMode")?.setValue(0);
            }
            if (saveMode == 'S') {
              this.form.get("managerDetails")?.reset();
              this.form.get("teamId")?.reset();
              this.form.get("sub_team_code")?.reset();
              this.form.get("planType")?.reset();
              this.form.get("selectedBrandId")?.reset();
              this.form.get("frequency")?.reset();
              this.form.get("allocationMode")?.reset();
              this.form.get("copyAllocationFrom")?.reset();
              this.showStaffCount = false;
              this.headerFreezed = false;
              this.frequencyFreezed = false;
              this.disable = false;
              this.allocationId = 0;
              this.allocDocNo = "";
              this.disableEnterFor = false;
              this.disableForMonthly = false;
            }
            this.medicoUtility.toggleProgressBar(false);
          } else {
            this.medicoService.popup("Monthly Plan ", response.RESPONSE_MESSAGE);
            this.medicoUtility.toggleProgressBar(false);
          }
        });
      } else {
        this.medicoUtility.validateAllFields(this.form);
        this.medicoUtility.toggleProgressBar(false);
      }
      this.unlockProducts();
    }
    discardAllocation() {
      this.medicoUtility.toggleProgressBar(true);
      if (this.allocationId != 0 && this.allocationId != undefined) {
        this.allocationService.discardMonthlyAllocation(this.allocationId, this.finYearId).subscribe(response => {
          this.medicoService.popup("Monthly Plan ", response.RESPONSE_MESSAGE);
          this.resetPage();
          this.medicoUtility.toggleProgressBar(false);
        });
      } else {
        this.medicoService.confirmBox("Message", "Please Save!", "Ok", "");
        this.medicoUtility.toggleProgressBar(false);
      }
    }
    resetPage() {
      if (this.isAssistant == true) {
        this.form.get("managerDetails")?.reset();
        this.disableEnterFor = false;
      }
      this.form.get("teamId")?.reset();
      this.form.get("planType")?.reset();
      this.form.get("selectedBrandId")?.reset();
      if (this.session.USER_ROLE != this.constants.ROLE_WAREH) {
        this.form.get("frequency")?.reset();
        this.frequencyFreezed = false;
      }
      this.form.get("allocationMode")?.reset();
      this.form.get("copyAllocationFrom")?.reset();
      this.showStaffCount = false;
      this.headerFreezed = false;
      this.disable = false;
      this.allocationId = 0;
      this.allocDocNo = "";
      this.productList = new _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatTableDataSource();
      this.agEntDetails = new Array();
      this.enteredBrandsEnt = new Array();
      this.brands = new Array();
    }
    onNewAllocation() {
      //window.open(this.constants.SERVER_SERVLET_CONTEXT_NAME +"/allocation/monthlyEntry","_self");
      this.resetPage();
      this.allocationId = 0;
    }
    onClickOfView() {
      this.setValuesToSubmit();
      this.form.get("teModel")?.setValue(this.teModelUpdate);
      this.form.get("dmModel")?.setValue(this.dmModelUpdate);
      this.form.get("rmbModel")?.setValue(this.rmbModelUpdate);
      this.form.get("tmModel")?.setValue(this.tmModelUpdate);
      this.form.get("smModel")?.setValue(this.smModelUpdate);
      this.form.get("productIds")?.setValue(this.productIdsUpdate);
      this.form.get("allocationSaveMode")?.setValue("M");
      this.form.get("alloc_gen_ent_ids")?.setValue(this.alloc_gen_ent_ids);
      this.openDialog();
    }
    openDialog() {
      if (this.allocationId != 0 && this.allocationId != undefined) {
        this.allocationService.getAllocEntList(this.allocationId).subscribe(response => {
          this.agEntDetails = response.agEnt;
          this.enteredBrandsEnt = response.enteredBrands;
          this.setAllocEntList();
          if (this.allocEntList != null && this.allocEntList != undefined) {
            const dialogRef = this.dialog.open(AllocEntModifyComponentpg, {
              width: '1600px',
              height: '600px',
              data: {
                list: this.allocEntList,
                teModel: this.teModelUpdate,
                rbmModel: this.rmbModelUpdate,
                dmModel: this.dmModelUpdate,
                tmModel: this.tmModelUpdate,
                smModel: this.smModelUpdate,
                enteredBrandsEnt: this.enteredBrandsEnt,
                form: this.form
              }
            });
          } else {
            this.medicoService.popup("Message", "No Saved transactions found");
          }
        });
      }
    }
    freeToaddMoreBrand() {
      this.disable = false;
      this.form.get("planType")?.reset();
      this.form.get("brandIds")?.reset();
      this.brands = new Array();
      this.productList = new _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatTableDataSource();
      this.checkAllocHeaderExist();
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
    selectAll() {
      if (this.selectAlls === true) {
        this.headerIds = this.ids;
        this.checkedInd = true;
      } else {
        this.headerIds = new Array();
        this.checkedInd = false;
      }
    }
    showRegerateDetails() {
      this.allocationService.getSelfApprovalDataMonthly(this.session.EMP_ID, this.assistantInd).subscribe(response => {
        if (response.selfApprovalData != null && response.selfApprovalData != undefined) {
          this.generatedAlloc = new _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatTableDataSource(response.selfApprovalData.slice(0));
          const dialogRef = this.dialog.open(AllocRegenerateComponentpg, {
            width: '1000px',
            height: '475px',
            data: {
              list: this.generatedAlloc
            }
          });
          dialogRef.afterClosed().subscribe(result => {
            this.allocationId = result.allocationId;
            this.allocationService.regenerateAllocation(this.allocationId).subscribe(response => {
              this.allocationCycle = response.allocationCycle;
              if (this.allocationCycle == "1") {
                this.form.get("frequency")?.setValue("M");
              } else {
                this.form.get("frequency")?.setValue("A");
              }
              this.allocationId = response.alloc_gen_id;
              this.allocDocNo = response.alloc_doc_no;
              this.teamId = response.division;
              this.monthOfUsePeriodCode = response.monthOfUse;
              this.headerFreezed = true;
              this.disableEnterFor = true;
              this.frequencyFreezed = true;
              this.disable = false;
              this.form.get("planType")?.setValue("");
              this.form.get("teamId")?.setValue(this.teamId);
              this.form.get("monthOfUsePeriodCode")?.setValue(this.monthOfUsePeriodCode);
              this.brands = new Array();
              this.medicoUtility.toggleProgressBar(false);
            });
          });
        } else {
          this.medicoService.popup("Message", "No Saved transactions found");
        }
      });
    }
    periviousPeriodDetails() {
      this.medicoUtility.toggleProgressBar(true);
      this.exludeDiscProd = this.form.get("exludeDiscProd")?.value;
      this.zeroStockInd = this.form.get("zeroStockInd")?.value;
      if (this.form.get("copyAllocationFrom")?.value[0] == this.session.CURR_FIN_YEAR) {
        this.isPrivious = 'N';
      } else {
        this.isPrivious = 'Y';
      }
      this.allocationService.setProductListForMonthly(this.planTypeDesc, this.selectedBrands, this.exludeDiscProd, this.zeroStockInd, this.teamId, "0", this.allocationMonthPeriodCode, "A", this.finYearId, this.allocationId, this.isPrivious, this.form.get("copyAllocationFrom")?.value[1], this.form.get("copyAllocationFrom")?.value[0], this.form.get("frequency")?.value, this.session.EMP_ID).subscribe(response => {
        this.productDetails = response.productList;
        if (this.productDetails != undefined) {
          this.productList = new _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatTableDataSource(this.productDetails.slice(0));
          for (let i = 0; i < this.productDetails.length; i++) {
            this.teModel[i] = this.productDetails[i].plan_qty;
            this.dmModel[i] = 0;
            this.rmbModel[i] = 0;
            this.tmModel[i] = 0;
            this.smModel[i] = 0;
            this.totalAllocationModel[i] = 0;
            this.alloc_gen_ent_ids[i] = 0;
            this.totalStock[i] = this.productDetails[i].stock;
            this.balanceStockModel[i] = this.totalStock[i] - this.teModel[i];
            this.allocationSoFar[i] = this.productDetails[i].so_far_qty;
            this.productIds[i] = this.productDetails[i].smp_prod_id;
            this.brandIds[i] = this.productDetails[i].sa_group_id;
            this.claculateFields(i);
          }
        } else {
          this.productList = new _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatTableDataSource();
        }
        this.medicoUtility.toggleProgressBar(false);
      });
    }
    ngOnDestroy() {
      this.unlockProducts();
    }
    unlockProducts() {
      this.allocationService.unlockProducts().subscribe(response => {});
    }
    static ɵfac = function MonthlyAllocationEntryPgComponent_Factory(__ngFactoryType__) {
      return new (__ngFactoryType__ || MonthlyAllocationEntryPgComponent)(_angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵdirectiveInject"](_angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormBuilder), _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵdirectiveInject"](src_app_classes_medico_utility__WEBPACK_IMPORTED_MODULE_1__.MedicoUtility), _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵdirectiveInject"](src_app_services_medico_medico_service__WEBPACK_IMPORTED_MODULE_2__.MedicoService), _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵdirectiveInject"](src_app_services_allocation_allocation_service__WEBPACK_IMPORTED_MODULE_3__.AllocationService), _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵdirectiveInject"](_angular_material_dialog__WEBPACK_IMPORTED_MODULE_8__.MatDialog));
    };
    static ɵcmp = /*@__PURE__*/_angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵdefineComponent"]({
      type: MonthlyAllocationEntryPgComponent,
      selectors: [["app-monthly-allocation-entry-pg"]],
      viewQuery: function MonthlyAllocationEntryPgComponent_Query(rf, ctx) {
        if (rf & 1) {
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵviewQuery"](_angular_material_paginator__WEBPACK_IMPORTED_MODULE_9__.MatPaginator, 5);
        }
        if (rf & 2) {
          let _t;
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵqueryRefresh"](_t = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵloadQuery"]()) && (ctx.paginator = _t.first);
        }
      },
      decls: 127,
      vars: 75,
      consts: [[3, "formGroup"], [1, "pl-1", "pr-1", "pb-1", "container"], ["hideToggle", "", 3, "expanded"], ["fxLayout", "row", "fxLayout.lt-md", "column", 1, "mt-1"], ["fxFlex", "100%"], ["fxLayout", "row", "fxLayout.lt-md", "column", 1, "padding"], ["fxFlex", "16%"], ["fxLayout", "row", "fxLayout.lt-md", "column"], ["fxFlex", "12%", "appearance", "outline", 2, "margin-bottom", "-10px", 3, "ngClass"], ["formControlName", "managerDetails", 3, "selectionChange", "disabled"], [3, "value", 4, "ngFor", "ngForOf"], ["formControlName", "teamId", 3, "selectionChange", "disabled"], ["fxFlex", "14%", "appearance", "outline", 2, "margin-bottom", "-10px", 3, "ngClass"], ["formControlName", "sub_team_code", 3, "selectionChange", "disabled"], ["formControlName", "frequency", 3, "selectionChange", "disabled"], ["formControlName", "alloc_type"], ["formControlName", "planType", 3, "disabled"], [3, "value", "click", 4, "ngFor", "ngForOf"], ["formControlName", "selectedBrandId", "multiple", "", 3, "selectionChange", "disabled"], [3, "value"], ["formControlName", "allocationMode", 3, "selectionChange", "disabled"], ["formControlName", "allocationById", 3, "selectionChange", "disabled"], ["value", "0"], ["formControlName", "zeroStockInd", 3, "selectionChange"], ["formControlName", "exludeDiscProd", 3, "selectionChange"], ["formControlName", "fieldtype", 3, "selectionChange"], ["formControlName", "copyAllocationFrom", 3, "selectionChange", "disabled"], ["formControlName", "monthOfUsePeriodCode"], ["fxLayout", "row", "fxLayout.lt-md", "column", "class", "countRow ", 4, "ngIf"], ["fxLayout", "row", "fxLayout.lt-md", "column", "class", "padding", 4, "ngIf"], ["fxLayout", "row", 1, "mt-1", "mb-1"], ["type", "button", "matTooltip", "Choose a New Team/Manager", 3, "click"], ["type", "button", "matTooltip", "Click to save as draft", 3, "click"], ["type", "button", "matTooltip", "Click to see saved allocation", 3, "click"], ["type", "button", "matTooltip", "Click to Add More Brands", 3, "click"], ["type", "button", "matTooltip", "Click to generate fieldstaffwise data", 3, "click"], ["type", "button", "matTooltip", "Click to discard current  allocation", 3, "click"], ["type", "button", "matTooltip", "Click to Regenerate fieldstaffwise data", 3, "click"], ["type", "button", "routerLink", "/medico-user/home"], [3, "click", "value"], ["fxLayout", "row", "fxLayout.lt-md", "column", 1, "countRow"], ["fxFlex", "10%"], ["fxFlex", "20%"], ["fxFlex", "70%", 4, "ngIf"], ["fxFlex", "70%"], ["fxFlex", "12%"], ["fxFlex", "8%"], ["appNumbersOnly", "", "step", "0.01", "matInput", "", 1, "text-right", 3, "ngModelChange", "ngModel", "ngModelOptions"], [1, "table-container", "mat-elevation-z2", "width-only-100"], ["mat-table", "", "matSort", "", 1, "table-borders-right", "width-only-100", "thin-th", "mat-td-p", "mat-th-p", 3, "dataSource"], ["matColumnDef", "productCode"], ["mat-header-cell", "", "class", "lightViolet", 3, "ngStyle", 4, "matHeaderCellDef"], ["mat-cell", "", 4, "matCellDef"], ["matColumnDef", "productCode_"], ["mat-header-cell", "", "class", "lightViolet", 4, "matHeaderCellDef"], ["matColumnDef", "brand"], ["matColumnDef", "brand_"], ["matColumnDef", "prodDescription"], ["mat-cell", "", 3, "matTooltip", 4, "matCellDef"], ["matColumnDef", "prodDescription_"], ["matColumnDef", "pack"], ["mat-cell", "", "class", "text-right", 4, "matCellDef"], ["matColumnDef", "pack_"], ["matColumnDef", "minAllocationQty"], ["matColumnDef", "minAllocationQty_"], ["matColumnDef", "te"], ["mat-header-cell", "", "class", "lightGreen", 3, "ngStyle", 4, "matHeaderCellDef"], ["mat-cell", "", "class", "green", 4, "matCellDef"], ["matColumnDef", "te_"], ["mat-header-cell", "", "class", "lightGreen", 4, "matHeaderCellDef"], ["matColumnDef", "dm"], ["matColumnDef", "dm_"], ["matColumnDef", "rbm"], ["matColumnDef", "rbm_"], ["matColumnDef", "tm"], ["matColumnDef", "tm_"], ["matColumnDef", "sm"], ["matColumnDef", "sm_"], ["matColumnDef", "totatlAllocationQty"], ["matColumnDef", "totatlAllocationQty_"], ["matColumnDef", "totalStock"], ["matColumnDef", "totalStock_"], ["matColumnDef", "transitStock"], ["matColumnDef", "transitStock_"], ["matColumnDef", "allocationSoFar"], ["matColumnDef", "allocationSoFar_"], ["matColumnDef", "balanceStock"], ["mat-cell", "", "class", "text-right", 3, "ngClass", 4, "matCellDef"], ["matColumnDef", "balanceStock_"], ["matColumnDef", "productDetails"], ["matColumnDef", "allocation"], ["matColumnDef", "stock"], ["matColumnDef", "teCount"], ["matColumnDef", "dmCount"], ["matColumnDef", "rmbCount"], ["matColumnDef", "tmCount"], ["matColumnDef", "smCount"], ["matColumnDef", "no1"], ["matColumnDef", "no2"], ["matColumnDef", "no3"], ["matColumnDef", "no4"], ["matColumnDef", "no5"], ["matColumnDef", "no6"], ["matColumnDef", "no7"], ["matColumnDef", "no8"], ["matColumnDef", "no9"], ["matColumnDef", "no10"], ["mat-header-row", "", "class", "table-row", 4, "matHeaderRowDef", "matHeaderRowDefSticky"], ["mat-header-row", "", "class", "table-row", "mat-sort-header", "", 4, "matHeaderRowDef", "matHeaderRowDefSticky"], ["mat-row", "", "class", "table-row", 4, "matRowDef", "matRowDefColumns"], ["mat-header-cell", "", 1, "lightViolet", 3, "ngStyle"], ["mat-cell", ""], ["mat-header-cell", "", 1, "lightViolet"], ["mat-cell", "", 3, "matTooltip"], ["mat-cell", "", 1, "text-right"], ["mat-header-cell", "", 1, "lightGreen", 3, "ngStyle"], ["mat-cell", "", 1, "green"], ["appNumbersOnly", "", "step", "0.01", "matInput", "", 1, "text-right", 3, "ngModelChange", "change", "ngModel", "ngModelOptions"], ["mat-header-cell", "", 1, "lightGreen"], ["appNumbersOnly", "", "step", "0.01", "matInput", "", 1, "text-right", 2, "font-weight", "bold", "color", "#039", 3, "ngModelChange", "change", "ngModel", "ngModelOptions"], ["mat-cell", "", 1, "text-right", 3, "ngClass"], ["mat-header-row", "", 1, "table-row"], ["mat-header-row", "", "mat-sort-header", "", 1, "table-row"], ["mat-row", "", 1, "table-row"]],
      template: function MonthlyAllocationEntryPgComponent_Template(rf, ctx) {
        if (rf & 1) {
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "form", 0)(1, "div", 1)(2, "mat-accordion")(3, "mat-expansion-panel", 2)(4, "mat-expansion-panel-header")(5, "mat-panel-title")(6, "b");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](7, "Monthly Allocation Entry ");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](8, "mat-panel-description");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](9, " Document Number : ");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](10, "b");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](11);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()()();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](12, "div", 3)(13, "div", 4)(14, "div", 5)(15, "mat-label", 6)(16, "b");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](17, "Financial Year :");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](18, "mat-label", 6);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](19);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](20, "mat-label", 6)(21, "b");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](22, "Date of Allocation :");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](23, "mat-label", 6);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](24);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](25, "mat-label", 6)(26, "b");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](27, "Allocation Month :");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](28, "mat-label", 6);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](29);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()()()();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](30, "div", 7)(31, "div", 4)(32, "div", 5)(33, "mat-form-field", 8)(34, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](35);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](36, "mat-select", 9);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵlistener"]("selectionChange", function MonthlyAllocationEntryPgComponent_Template_mat_select_selectionChange_36_listener() {
            return ctx.setEmpDetails();
          });
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](37, MonthlyAllocationEntryPgComponent_mat_option_37_Template, 2, 5, "mat-option", 10);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](38, "mat-form-field", 8)(39, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](40, "Division");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](41, "mat-select", 11);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵlistener"]("selectionChange", function MonthlyAllocationEntryPgComponent_Template_mat_select_selectionChange_41_listener() {
            return ctx.freeToaddMoreBrand();
          });
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](42, MonthlyAllocationEntryPgComponent_mat_option_42_Template, 2, 2, "mat-option", 10);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](43, "mat-form-field", 12)(44, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](45, "Team");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](46, "mat-select", 13);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵlistener"]("selectionChange", function MonthlyAllocationEntryPgComponent_Template_mat_select_selectionChange_46_listener() {
            return ctx.freeToaddMoreBrand();
          });
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](47, MonthlyAllocationEntryPgComponent_mat_option_47_Template, 2, 2, "mat-option", 10);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](48, "mat-form-field", 12)(49, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](50, "Allocation Type");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](51, "mat-select", 14);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵlistener"]("selectionChange", function MonthlyAllocationEntryPgComponent_Template_mat_select_selectionChange_51_listener() {
            return ctx.checkAllocHeaderExist();
          });
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](52, MonthlyAllocationEntryPgComponent_mat_option_52_Template, 2, 2, "mat-option", 10);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](53, "mat-form-field", 12)(54, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](55, "Allocation For");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](56, "mat-select", 15);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](57, MonthlyAllocationEntryPgComponent_mat_option_57_Template, 2, 2, "mat-option", 10);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](58, "mat-form-field", 12)(59, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](60, "Product Type");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](61, "mat-select", 16);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](62, MonthlyAllocationEntryPgComponent_mat_option_62_Template, 2, 2, "mat-option", 17);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](63, "mat-form-field", 12)(64, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](65, "My Brands");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](66, "mat-select", 18);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵlistener"]("selectionChange", function MonthlyAllocationEntryPgComponent_Template_mat_select_selectionChange_66_listener() {
            return ctx.setProductList();
          });
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](67, "mat-option", 19);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](68, "All");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](69, MonthlyAllocationEntryPgComponent_mat_option_69_Template, 2, 2, "mat-option", 10);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()()();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](70, "div", 5)(71, "mat-form-field", 8)(72, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](73, "Allocation By");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](74, "mat-select", 20);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵlistener"]("selectionChange", function MonthlyAllocationEntryPgComponent_Template_mat_select_selectionChange_74_listener() {
            return ctx.setAllocationByList();
          });
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](75, MonthlyAllocationEntryPgComponent_mat_option_75_Template, 2, 2, "mat-option", 10);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](76, "mat-form-field", 8)(77, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](78);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](79, "mat-select", 21);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵlistener"]("selectionChange", function MonthlyAllocationEntryPgComponent_Template_mat_select_selectionChange_79_listener() {
            return ctx.setAllocationByCount();
          });
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](80, "mat-option", 22);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](81, "All");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](82, MonthlyAllocationEntryPgComponent_mat_option_82_Template, 2, 2, "mat-option", 10);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](83, "mat-form-field", 12)(84, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](85, "Exclude Zero Stock");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](86, "mat-select", 23);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵlistener"]("selectionChange", function MonthlyAllocationEntryPgComponent_Template_mat_select_selectionChange_86_listener() {
            return ctx.setProductList();
          });
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](87, MonthlyAllocationEntryPgComponent_mat_option_87_Template, 2, 2, "mat-option", 10);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](88, "mat-form-field", 12)(89, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](90, "Exclude Discontinued Product");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](91, "mat-select", 24);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵlistener"]("selectionChange", function MonthlyAllocationEntryPgComponent_Template_mat_select_selectionChange_91_listener() {
            return ctx.setProductList();
          });
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](92, MonthlyAllocationEntryPgComponent_mat_option_92_Template, 2, 2, "mat-option", 10);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](93, "mat-form-field", 12)(94, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](95, "Include Training");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](96, "mat-select", 25);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵlistener"]("selectionChange", function MonthlyAllocationEntryPgComponent_Template_mat_select_selectionChange_96_listener() {
            return ctx.showSmTmForEntry();
          });
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](97, MonthlyAllocationEntryPgComponent_mat_option_97_Template, 2, 2, "mat-option", 10);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](98, "mat-form-field", 12)(99, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](100, "Copy Allocation");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](101, "mat-select", 26);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵlistener"]("selectionChange", function MonthlyAllocationEntryPgComponent_Template_mat_select_selectionChange_101_listener() {
            return ctx.periviousPeriodDetails();
          });
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](102, MonthlyAllocationEntryPgComponent_mat_option_102_Template, 2, 5, "mat-option", 10);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](103, "mat-form-field", 12)(104, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](105, "Month Of Use");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](106, "mat-select", 27);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](107, MonthlyAllocationEntryPgComponent_mat_option_107_Template, 2, 2, "mat-option", 10);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()()()()()()();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](108, MonthlyAllocationEntryPgComponent_div_108_Template, 14, 4, "div", 28)(109, MonthlyAllocationEntryPgComponent_div_109_Template, 120, 16, "div", 29);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](110, "div", 30)(111, "button", 31);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵlistener"]("click", function MonthlyAllocationEntryPgComponent_Template_button_click_111_listener() {
            return ctx.onNewAllocation();
          });
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](112, "New");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](113, "button", 32);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵlistener"]("click", function MonthlyAllocationEntryPgComponent_Template_button_click_113_listener() {
            return ctx.saveMonthlyAllocation("E");
          });
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](114, "Save as Draft");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](115, "button", 33);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵlistener"]("click", function MonthlyAllocationEntryPgComponent_Template_button_click_115_listener() {
            return ctx.onClickOfView();
          });
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](116, "View Saved Allocation");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](117, "button", 34);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵlistener"]("click", function MonthlyAllocationEntryPgComponent_Template_button_click_117_listener() {
            return ctx.freeToaddMoreBrand();
          });
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](118, "Add More Brands");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](119, "button", 35);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵlistener"]("click", function MonthlyAllocationEntryPgComponent_Template_button_click_119_listener() {
            return ctx.saveMonthlyAllocation("S");
          });
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](120, "Generate Fieldstaffwise");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](121, "button", 36);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵlistener"]("click", function MonthlyAllocationEntryPgComponent_Template_button_click_121_listener() {
            return ctx.discardAllocation();
          });
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](122, "Discard");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](123, "button", 37);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵlistener"]("click", function MonthlyAllocationEntryPgComponent_Template_button_click_123_listener() {
            return ctx.showRegerateDetails();
          });
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](124, "Regenerate Fieldstaffwise");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](125, "button", 38);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](126, "Exit");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()()()();
        }
        if (rf & 2) {
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("formGroup", ctx.form);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("expanded", true);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](8);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](ctx.allocDocNo);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](8);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](ctx.financialYear);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](5);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](ctx.allocationDate);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](5);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](ctx.allocationMonth);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵclassMapInterpolate1"]("mt-1 ", ctx.constants.page_div_class, "");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngClass", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](2);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](ctx.enterFor);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("disabled", ctx.disableEnterFor);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngForOf", ctx.managerList);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngClass", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("disabled", ctx.headerFreezed);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngForOf", ctx.teams);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngClass", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("disabled", ctx.subTeamFreezed);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngForOf", ctx.subTeamList);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngClass", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("disabled", ctx.frequencyFreezed);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngForOf", ctx.frequency);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngClass", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](4);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngForOf", ctx.allocation_for);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngClass", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("disabled", ctx.disable);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngForOf", ctx.planType);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngClass", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("disabled", ctx.disable);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("value", 0);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](2);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngForOf", ctx.brands);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](2);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngClass", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("disabled", ctx.disableForMonthly);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngForOf", ctx.allocationMode);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngClass", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](2);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtextInterpolate"](ctx.allocationByName);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("disabled", ctx.disableForMonthly2);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngForOf", ctx.allocationBy);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngClass", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](4);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngForOf", ctx.includeExclude);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngClass", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](4);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngForOf", ctx.includeExclude);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngClass", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](4);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngForOf", ctx.fieldOptions);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngClass", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("disabled", ctx.disableForAdditional);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngForOf", ctx.copyAllocation);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngClass", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](4);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngForOf", ctx.monthOfUse);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngIf", ctx.showStaffCount);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngIf", ctx.showStaffCount);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](2);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵclassMapInterpolate1"]("mr-r-10 grad-btn ", ctx.constants.exit_btn_class, "");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](2);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵclassMapInterpolate1"]("mr-r-10 grad-btn ", ctx.constants.exit_btn_class, "");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](2);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵclassMapInterpolate1"]("mr-r-10 grad-btn ", ctx.constants.exit_btn_class, "");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](2);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵclassMapInterpolate1"]("mr-r-10 grad-btn ", ctx.constants.save_btn_class, "");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](2);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵclassMapInterpolate1"]("mr-r-10 grad-btn ", ctx.constants.save_btn_class, "");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](2);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵclassMapInterpolate1"]("mr-r-10 grad-btn ", ctx.constants.discard_btn_class, "");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](2);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵclassMapInterpolate1"]("mr-r-10 grad-btn ", ctx.constants.save_btn_class, "");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](2);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵclassMapInterpolate1"]("mr-r-10 grad-btn ", ctx.constants.exit_btn_class, "");
        }
      },
      dependencies: [_angular_router__WEBPACK_IMPORTED_MODULE_10__.RouterLink, _angular_common__WEBPACK_IMPORTED_MODULE_11__.NgClass, _angular_common__WEBPACK_IMPORTED_MODULE_11__.NgForOf, _angular_common__WEBPACK_IMPORTED_MODULE_11__.NgIf, _angular_common__WEBPACK_IMPORTED_MODULE_11__.NgStyle, _angular_forms__WEBPACK_IMPORTED_MODULE_6__["ɵNgNoValidate"], _angular_forms__WEBPACK_IMPORTED_MODULE_6__.DefaultValueAccessor, _angular_forms__WEBPACK_IMPORTED_MODULE_6__.NgControlStatus, _angular_forms__WEBPACK_IMPORTED_MODULE_6__.NgControlStatusGroup, _angular_forms__WEBPACK_IMPORTED_MODULE_6__.NgModel, _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormGroupDirective, _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControlName, _angular_material_form_field__WEBPACK_IMPORTED_MODULE_12__.MatFormField, _angular_material_form_field__WEBPACK_IMPORTED_MODULE_12__.MatLabel, _angular_flex_layout_flex__WEBPACK_IMPORTED_MODULE_13__.DefaultLayoutDirective, _angular_flex_layout_flex__WEBPACK_IMPORTED_MODULE_13__.DefaultFlexDirective, _angular_flex_layout_extended__WEBPACK_IMPORTED_MODULE_14__.DefaultClassDirective, _angular_flex_layout_extended__WEBPACK_IMPORTED_MODULE_14__.DefaultStyleDirective, _angular_material_input__WEBPACK_IMPORTED_MODULE_15__.MatInput, _angular_material_expansion__WEBPACK_IMPORTED_MODULE_16__.MatAccordion, _angular_material_expansion__WEBPACK_IMPORTED_MODULE_16__.MatExpansionPanel, _angular_material_expansion__WEBPACK_IMPORTED_MODULE_16__.MatExpansionPanelHeader, _angular_material_expansion__WEBPACK_IMPORTED_MODULE_16__.MatExpansionPanelTitle, _angular_material_expansion__WEBPACK_IMPORTED_MODULE_16__.MatExpansionPanelDescription, _angular_material_select__WEBPACK_IMPORTED_MODULE_17__.MatSelect, _angular_material_core__WEBPACK_IMPORTED_MODULE_18__.MatOption, _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatTable, _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatHeaderCellDef, _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatHeaderRowDef, _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatColumnDef, _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatCellDef, _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatRowDef, _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatHeaderCell, _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatCell, _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatHeaderRow, _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatRow, _angular_material_tooltip__WEBPACK_IMPORTED_MODULE_19__.MatTooltip, _directives_utility_numbers_only_directive__WEBPACK_IMPORTED_MODULE_4__.NumbersOnlyDirective, _angular_common__WEBPACK_IMPORTED_MODULE_11__.SlicePipe],
      styles: [_c8]
    });
  }
  return MonthlyAllocationEntryPgComponent;
})();
let AllocEntModifyComponentpg = /*#__PURE__*/(() => {
  class AllocEntModifyComponentpg {
    dialogRef;
    data;
    fb;
    medicoUtility;
    medicoService;
    allocationService;
    dialog;
    constants = new src_app_classes_constants__WEBPACK_IMPORTED_MODULE_0__.Constants();
    session;
    brandList = new Array();
    entredBrands = new Array();
    brandId;
    checkUser;
    form;
    constructor(dialogRef, data, fb, medicoUtility, medicoService, allocationService, dialog) {
      this.dialogRef = dialogRef;
      this.data = data;
      this.fb = fb;
      this.medicoUtility = medicoUtility;
      this.medicoService = medicoService;
      this.allocationService = allocationService;
      this.dialog = dialog;
      this.form = this.fb.group({
        entredBrands: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl("")
      });
      this.brandList = data.enteredBrandsEnt;
    }
    paginator;
    displayedColumns = ["allocationByName", "brand", "prodDescription", "te", "dm", "rbm", "tm", "sm"];
    sort;
    applyFilter(event) {
      const target = event.target;
      const value = target ? target.value : '';
      this.data.list.filter = value.trim().toLowerCase();
    }
    searchByBrand() {
      this.entredBrands = this.form.get("entredBrands")?.value;
      this.brandId = this.entredBrands[0];
      this.applyFilter(this.entredBrands[1]);
    }
    update() {
      this.dialogRef.close();
      this.medicoUtility.toggleProgressBar(true);
      this.allocationService.saveMonthlyAllocation(this.data.form).subscribe(response => {
        this.medicoUtility.toggleProgressBar(true);
        if (response.DATA_SAVED) {
          this.medicoService.popup("Monthly Plan  Updated", response.RESPONSE_MESSAGE);
          this.medicoUtility.toggleProgressBar(false);
        } else {
          this.medicoService.popup("Monthly Plan ", response.RESPONSE_MESSAGE);
          this.medicoUtility.toggleProgressBar(false);
        }
      });
    }
    discard() {
      this.dialogRef.close();
      this.data.form.get("brandId")?.setValue(this.brandId);
      //  this.allocationService.discardMonthlyAllocationByBrand(this.data.form).subscribe(response => {
      //   this.medicoUtility.toggleProgressBar(true);
      //     if (response.DATA_SAVED) {
      //       this.medicoService.popup("Monthly Plan  Updated", response.RESPONSE_MESSAGE);
      //       this.medicoUtility.toggleProgressBar(false);
      //     }
      //     else {
      //       this.medicoService.popup("Monthly Plan ", response.RESPONSE_MESSAGE);
      //       this.medicoUtility.toggleProgressBar(false);
      //     }
      // });
    }
    static ɵfac = function AllocEntModifyComponentpg_Factory(__ngFactoryType__) {
      return new (__ngFactoryType__ || AllocEntModifyComponentpg)(_angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵdirectiveInject"](_angular_material_dialog__WEBPACK_IMPORTED_MODULE_8__.MatDialogRef), _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵdirectiveInject"](_angular_material_dialog__WEBPACK_IMPORTED_MODULE_8__.MAT_DIALOG_DATA), _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵdirectiveInject"](_angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormBuilder), _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵdirectiveInject"](src_app_classes_medico_utility__WEBPACK_IMPORTED_MODULE_1__.MedicoUtility), _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵdirectiveInject"](src_app_services_medico_medico_service__WEBPACK_IMPORTED_MODULE_2__.MedicoService), _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵdirectiveInject"](src_app_services_allocation_allocation_service__WEBPACK_IMPORTED_MODULE_3__.AllocationService), _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵdirectiveInject"](_angular_material_dialog__WEBPACK_IMPORTED_MODULE_8__.MatDialog));
    };
    static ɵcmp = /*@__PURE__*/_angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵdefineComponent"]({
      type: AllocEntModifyComponentpg,
      selectors: [["allocEnt-details-dialog"]],
      viewQuery: function AllocEntModifyComponentpg_Query(rf, ctx) {
        if (rf & 1) {
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵviewQuery"](_angular_material_paginator__WEBPACK_IMPORTED_MODULE_9__.MatPaginator, 5);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵviewQuery"](_angular_material_sort__WEBPACK_IMPORTED_MODULE_20__.MatSort, 5);
        }
        if (rf & 2) {
          let _t;
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵqueryRefresh"](_t = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵloadQuery"]()) && (ctx.paginator = _t.first);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵqueryRefresh"](_t = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵloadQuery"]()) && (ctx.sort = _t.first);
        }
      },
      decls: 49,
      vars: 14,
      consts: [["mat-dialog-title", ""], ["mat-dialog-content", ""], [3, "formGroup"], ["fxLayout", "row", "fxLayout.lt-md", "column", 1, "pl-1"], ["fxFlex", "30%", "appearance", "outline", 3, "ngClass.gt-sm"], ["matInput", "", "placeholder", "Filter", 3, "keyup"], ["fxFlex", "16%", "appearance", "outline", 2, "margin-bottom", "-10px", 3, "ngClass.gt-sm"], ["formControlName", "entredBrands", 3, "selectionChange"], [3, "value", 4, "ngFor", "ngForOf"], ["fxLayout", "row", "fxLayout.lt-md", "column", 1, "padding"], [1, "table-container", "mat-elevation-z2", "width-only-100"], ["mat-table", "", "matSort", "", 1, "table-borders-right", "width-only-100", "thin-th", "mat-td-p", "mat-th-p", 3, "dataSource"], ["matColumnDef", "allocationByName"], ["mat-header-cell", "", "class", "lightViolet", 4, "matHeaderCellDef"], ["mat-cell", "", 4, "matCellDef"], ["matColumnDef", "brand"], ["matColumnDef", "prodDescription"], ["mat-cell", "", 3, "matTooltip", 4, "matCellDef"], ["matColumnDef", "te"], ["mat-cell", "", "class", "green", 4, "matCellDef"], ["matColumnDef", "dm"], ["matColumnDef", "rbm"], ["matColumnDef", "tm"], ["matColumnDef", "sm"], ["mat-header-row", "", "class", "table-row", "mat-sort-header", "", 4, "matHeaderRowDef", "matHeaderRowDefSticky"], ["mat-row", "", "class", "table-row", 4, "matRowDef", "matRowDefColumns"], ["mat-dialog-actions", "", 1, "mt-1", "mb-1"], ["type", "button", "value", "Update", 3, "click"], ["type", "button", 3, "click"], [3, "value"], ["mat-header-cell", "", 1, "lightViolet"], ["mat-cell", ""], ["mat-cell", "", 3, "matTooltip"], ["mat-cell", "", 1, "green"], ["appNumbersOnly", "", "step", "0.01", "matInput", "", 1, "text-right", 3, "ngModelChange", "ngModel", "ngModelOptions"], ["mat-header-row", "", "mat-sort-header", "", 1, "table-row"], ["mat-row", "", 1, "table-row"]],
      template: function AllocEntModifyComponentpg_Template(rf, ctx) {
        if (rf & 1) {
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "h1", 0);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1, "Saved Details In Draft Mode");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](2, "div", 1)(3, "form", 2)(4, "div", 3)(5, "mat-form-field", 4)(6, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](7, "Search");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](8, "input", 5);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵlistener"]("keyup", function AllocEntModifyComponentpg_Template_input_keyup_8_listener($event) {
            return ctx.applyFilter($event);
          });
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](9, "mat-form-field", 6)(10, "mat-label");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](11, "Entered Brands");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](12, "mat-select", 7);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵlistener"]("selectionChange", function AllocEntModifyComponentpg_Template_mat_select_selectionChange_12_listener() {
            return ctx.searchByBrand();
          });
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](13, AllocEntModifyComponentpg_mat_option_13_Template, 2, 5, "mat-option", 8);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()()();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](14, "div", 9)(15, "div", 10)(16, "table", 11);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](17, 12);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](18, AllocEntModifyComponentpg_th_18_Template, 2, 0, "th", 13)(19, AllocEntModifyComponentpg_td_19_Template, 2, 1, "td", 14);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](20, 15);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](21, AllocEntModifyComponentpg_th_21_Template, 2, 0, "th", 13)(22, AllocEntModifyComponentpg_td_22_Template, 2, 1, "td", 14);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](23, 16);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](24, AllocEntModifyComponentpg_th_24_Template, 2, 0, "th", 13)(25, AllocEntModifyComponentpg_td_25_Template, 3, 6, "td", 17);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](26, 18);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](27, AllocEntModifyComponentpg_th_27_Template, 2, 0, "th", 13)(28, AllocEntModifyComponentpg_td_28_Template, 2, 3, "td", 19);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](29, 20);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](30, AllocEntModifyComponentpg_th_30_Template, 2, 0, "th", 13)(31, AllocEntModifyComponentpg_td_31_Template, 2, 3, "td", 19);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](32, 21);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](33, AllocEntModifyComponentpg_th_33_Template, 2, 0, "th", 13)(34, AllocEntModifyComponentpg_td_34_Template, 2, 3, "td", 19);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](35, "ss ");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](36, 22);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](37, AllocEntModifyComponentpg_th_37_Template, 2, 0, "th", 13)(38, AllocEntModifyComponentpg_td_38_Template, 2, 3, "td", 19);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](39, 23);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](40, AllocEntModifyComponentpg_th_40_Template, 2, 0, "th", 13)(41, AllocEntModifyComponentpg_td_41_Template, 2, 3, "td", 19);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](42, AllocEntModifyComponentpg_tr_42_Template, 1, 0, "tr", 24)(43, AllocEntModifyComponentpg_tr_43_Template, 1, 0, "tr", 25);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()()()()();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](44, "div", 26)(45, "button", 27);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵlistener"]("click", function AllocEntModifyComponentpg_Template_button_click_45_listener() {
            return ctx.update();
          });
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](46, "Save");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](47, "button", 28);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵlistener"]("click", function AllocEntModifyComponentpg_Template_button_click_47_listener() {
            return ctx.discard();
          });
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](48, "Cancel");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
        }
        if (rf & 2) {
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("formGroup", ctx.form);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](2);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngClass.gt-sm", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](4);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngClass.gt-sm", "mr-r-10");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](4);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngForOf", ctx.brandList);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("dataSource", ctx.data.list);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](26);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("matHeaderRowDef", ctx.displayedColumns)("matHeaderRowDefSticky", true);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("matRowDefColumns", ctx.displayedColumns);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](2);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵclassMapInterpolate1"]("mr-r-10 grad-btn ", ctx.constants.approve_btn_class, "");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](2);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵclassMapInterpolate1"]("mr-r-10 grad-btn ", ctx.constants.discard_btn_class, "");
        }
      },
      dependencies: [_angular_common__WEBPACK_IMPORTED_MODULE_11__.NgForOf, _angular_forms__WEBPACK_IMPORTED_MODULE_6__["ɵNgNoValidate"], _angular_forms__WEBPACK_IMPORTED_MODULE_6__.DefaultValueAccessor, _angular_forms__WEBPACK_IMPORTED_MODULE_6__.NgControlStatus, _angular_forms__WEBPACK_IMPORTED_MODULE_6__.NgControlStatusGroup, _angular_forms__WEBPACK_IMPORTED_MODULE_6__.NgModel, _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormGroupDirective, _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControlName, _angular_material_form_field__WEBPACK_IMPORTED_MODULE_12__.MatFormField, _angular_material_form_field__WEBPACK_IMPORTED_MODULE_12__.MatLabel, _angular_flex_layout_flex__WEBPACK_IMPORTED_MODULE_13__.DefaultLayoutDirective, _angular_flex_layout_flex__WEBPACK_IMPORTED_MODULE_13__.DefaultFlexDirective, _angular_flex_layout_extended__WEBPACK_IMPORTED_MODULE_14__.DefaultClassDirective, _angular_material_input__WEBPACK_IMPORTED_MODULE_15__.MatInput, _angular_material_select__WEBPACK_IMPORTED_MODULE_17__.MatSelect, _angular_material_core__WEBPACK_IMPORTED_MODULE_18__.MatOption, _angular_material_dialog__WEBPACK_IMPORTED_MODULE_8__.MatDialogTitle, _angular_material_dialog__WEBPACK_IMPORTED_MODULE_8__.MatDialogActions, _angular_material_dialog__WEBPACK_IMPORTED_MODULE_8__.MatDialogContent, _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatTable, _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatHeaderCellDef, _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatHeaderRowDef, _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatColumnDef, _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatCellDef, _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatRowDef, _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatHeaderCell, _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatCell, _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatHeaderRow, _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatRow, _angular_material_tooltip__WEBPACK_IMPORTED_MODULE_19__.MatTooltip, _directives_utility_numbers_only_directive__WEBPACK_IMPORTED_MODULE_4__.NumbersOnlyDirective, _angular_common__WEBPACK_IMPORTED_MODULE_11__.SlicePipe],
      styles: [_c8]
    });
  }
  return AllocEntModifyComponentpg;
})();
let FieldstaffDetailsComponentpg = /*#__PURE__*/(() => {
  class FieldstaffDetailsComponentpg {
    dialogRef;
    data;
    medicoUtility;
    medicoService;
    constants = new src_app_classes_constants__WEBPACK_IMPORTED_MODULE_0__.Constants();
    session;
    checkUser;
    constructor(dialogRef, data, medicoUtility, medicoService) {
      this.dialogRef = dialogRef;
      this.data = data;
      this.medicoUtility = medicoUtility;
      this.medicoService = medicoService;
      this.session = this.medicoUtility.getSessionVariables();
    }
    task_id = new Array();
    paginator;
    sort;
    static ɵfac = function FieldstaffDetailsComponentpg_Factory(__ngFactoryType__) {
      return new (__ngFactoryType__ || FieldstaffDetailsComponentpg)(_angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵdirectiveInject"](_angular_material_dialog__WEBPACK_IMPORTED_MODULE_8__.MatDialogRef), _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵdirectiveInject"](_angular_material_dialog__WEBPACK_IMPORTED_MODULE_8__.MAT_DIALOG_DATA), _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵdirectiveInject"](src_app_classes_medico_utility__WEBPACK_IMPORTED_MODULE_1__.MedicoUtility), _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵdirectiveInject"](src_app_services_medico_medico_service__WEBPACK_IMPORTED_MODULE_2__.MedicoService));
    };
    static ɵcmp = /*@__PURE__*/_angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵdefineComponent"]({
      type: FieldstaffDetailsComponentpg,
      selectors: [["fieldstaff-detail-dialog"]],
      viewQuery: function FieldstaffDetailsComponentpg_Query(rf, ctx) {
        if (rf & 1) {
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵviewQuery"](_angular_material_paginator__WEBPACK_IMPORTED_MODULE_9__.MatPaginator, 5);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵviewQuery"](_angular_material_sort__WEBPACK_IMPORTED_MODULE_20__.MatSort, 5);
        }
        if (rf & 2) {
          let _t;
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵqueryRefresh"](_t = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵloadQuery"]()) && (ctx.paginator = _t.first);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵqueryRefresh"](_t = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵloadQuery"]()) && (ctx.sort = _t.first);
        }
      },
      decls: 16,
      vars: 1,
      consts: [["mat-dialog-title", ""], ["mat-dialog-content", ""], [1, "mytable", "table-full-width"], [4, "ngFor", "ngForOf"], [1, "text-left"]],
      template: function FieldstaffDetailsComponentpg_Template(rf, ctx) {
        if (rf & 1) {
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "h1", 0);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1, "Following fieldstaff does not have CFA link");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](2, "div", 1)(3, "table", 2)(4, "thead")(5, "tr")(6, "th");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](7, "Staff Cd");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](8, "th");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](9, "Staff Name");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](10, "th");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](11, "Territory");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](12, "th");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](13, "Company");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()()();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](14, "tbody");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](15, FieldstaffDetailsComponentpg_tr_15_Template, 9, 4, "tr", 3);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()()();
        }
        if (rf & 2) {
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](15);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("ngForOf", ctx.data.list);
        }
      },
      dependencies: [_angular_common__WEBPACK_IMPORTED_MODULE_11__.NgForOf, _angular_material_dialog__WEBPACK_IMPORTED_MODULE_8__.MatDialogTitle, _angular_material_dialog__WEBPACK_IMPORTED_MODULE_8__.MatDialogContent],
      encapsulation: 2
    });
  }
  return FieldstaffDetailsComponentpg;
})();
let AllocRegenerateComponentpg = /*#__PURE__*/(() => {
  class AllocRegenerateComponentpg {
    dialogRef;
    data;
    fb;
    medicoUtility;
    medicoService;
    allocationService;
    dialog;
    constants = new src_app_classes_constants__WEBPACK_IMPORTED_MODULE_0__.Constants();
    session;
    allocationId = "0";
    form;
    constructor(dialogRef, data, fb, medicoUtility, medicoService, allocationService, dialog) {
      this.dialogRef = dialogRef;
      this.data = data;
      this.fb = fb;
      this.medicoUtility = medicoUtility;
      this.medicoService = medicoService;
      this.allocationService = allocationService;
      this.dialog = dialog;
      this.form = this.fb.group({
        entredBrands: new _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormControl("")
      });
    }
    paginator;
    displayedColumns = ["select", "dateOfAlloc", "division", "docNumber", "company"];
    sort;
    onSelect(row) {
      this.allocationId = row.allocationId;
    }
    confirmRegenerate() {
      if (this.allocationId != "0") {
        this.medicoService.confirmBox("Confirmation", "Regeneration will delete all modified data?", "Ok", "Cancel").subscribe(result => {
          if (result == true) {
            this.dialogRef.close({
              allocationId: this.allocationId
            });
          }
        });
      } else {
        this.medicoService.popup("Message", "Select document to Regenerate");
      }
    }
    static ɵfac = function AllocRegenerateComponentpg_Factory(__ngFactoryType__) {
      return new (__ngFactoryType__ || AllocRegenerateComponentpg)(_angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵdirectiveInject"](_angular_material_dialog__WEBPACK_IMPORTED_MODULE_8__.MatDialogRef), _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵdirectiveInject"](_angular_material_dialog__WEBPACK_IMPORTED_MODULE_8__.MAT_DIALOG_DATA), _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵdirectiveInject"](_angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormBuilder), _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵdirectiveInject"](src_app_classes_medico_utility__WEBPACK_IMPORTED_MODULE_1__.MedicoUtility), _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵdirectiveInject"](src_app_services_medico_medico_service__WEBPACK_IMPORTED_MODULE_2__.MedicoService), _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵdirectiveInject"](src_app_services_allocation_allocation_service__WEBPACK_IMPORTED_MODULE_3__.AllocationService), _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵdirectiveInject"](_angular_material_dialog__WEBPACK_IMPORTED_MODULE_8__.MatDialog));
    };
    static ɵcmp = /*@__PURE__*/_angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵdefineComponent"]({
      type: AllocRegenerateComponentpg,
      selectors: [["alloc-regenerate-dialog"]],
      viewQuery: function AllocRegenerateComponentpg_Query(rf, ctx) {
        if (rf & 1) {
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵviewQuery"](_angular_material_paginator__WEBPACK_IMPORTED_MODULE_9__.MatPaginator, 5);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵviewQuery"](_angular_material_sort__WEBPACK_IMPORTED_MODULE_20__.MatSort, 5);
        }
        if (rf & 2) {
          let _t;
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵqueryRefresh"](_t = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵloadQuery"]()) && (ctx.paginator = _t.first);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵqueryRefresh"](_t = _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵloadQuery"]()) && (ctx.sort = _t.first);
        }
      },
      decls: 27,
      vars: 8,
      consts: [["mat-dialog-title", ""], ["mat-dialog-content", ""], [3, "formGroup"], ["fxLayout", "row", "fxLayout.lt-md", "column", 1, "padding"], [1, "table-container", "mat-elevation-z2", "width-only-100"], ["mat-table", "", "matSort", "", 1, "table-borders-right", "width-only-100", "thin-th", "mat-td-p", "mat-th-p", 3, "dataSource"], ["matColumnDef", "select"], ["mat-header-cell", "", "class", "lightViolet", 4, "matHeaderCellDef"], ["mat-cell", "", "class", "text-center", 4, "matCellDef"], ["matColumnDef", "dateOfAlloc"], ["matColumnDef", "division"], ["mat-cell", "", 4, "matCellDef"], ["matColumnDef", "docNumber"], ["matColumnDef", "company"], ["mat-header-row", "", "class", "table-row", "mat-sort-header", "", 4, "matHeaderRowDef", "matHeaderRowDefSticky"], ["mat-row", "", "class", "table-row", 4, "matRowDef", "matRowDefColumns"], ["mat-dialog-actions", "", 1, "mt-1", "mb-1"], ["type", "button", 3, "click"], ["mat-header-cell", "", 1, "lightViolet"], ["mat-cell", "", 1, "text-center"], [3, "change"], ["mat-cell", ""], ["mat-header-row", "", "mat-sort-header", "", 1, "table-row"], ["mat-row", "", 1, "table-row"]],
      template: function AllocRegenerateComponentpg_Template(rf, ctx) {
        if (rf & 1) {
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](0, "h1", 0);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](1, "Generated Allocation");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](2, "div", 1)(3, "form", 2)(4, "div", 3)(5, "div", 4)(6, "table", 5);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](7, 6);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](8, AllocRegenerateComponentpg_th_8_Template, 2, 0, "th", 7)(9, AllocRegenerateComponentpg_td_9_Template, 2, 0, "td", 8);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](10, 9);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](11, AllocRegenerateComponentpg_th_11_Template, 2, 0, "th", 7)(12, AllocRegenerateComponentpg_td_12_Template, 2, 1, "td", 8);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](13, 10);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](14, AllocRegenerateComponentpg_th_14_Template, 2, 0, "th", 7)(15, AllocRegenerateComponentpg_td_15_Template, 2, 1, "td", 11);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](16, 12);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](17, AllocRegenerateComponentpg_th_17_Template, 2, 0, "th", 7)(18, AllocRegenerateComponentpg_td_18_Template, 2, 1, "td", 8);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerStart"](19, 13);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](20, AllocRegenerateComponentpg_th_20_Template, 2, 0, "th", 7)(21, AllocRegenerateComponentpg_td_21_Template, 2, 1, "td", 8);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementContainerEnd"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtemplate"](22, AllocRegenerateComponentpg_tr_22_Template, 1, 0, "tr", 14)(23, AllocRegenerateComponentpg_tr_23_Template, 1, 0, "tr", 15);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()()()()();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementStart"](24, "div", 16)(25, "button", 17);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵlistener"]("click", function AllocRegenerateComponentpg_Template_button_click_25_listener() {
            return ctx.confirmRegenerate();
          });
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵtext"](26, "Regenerate");
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵelementEnd"]()();
        }
        if (rf & 2) {
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("formGroup", ctx.form);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](3);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("dataSource", ctx.data.list);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](16);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("matHeaderRowDef", ctx.displayedColumns)("matHeaderRowDefSticky", true);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"]();
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵproperty"]("matRowDefColumns", ctx.displayedColumns);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵadvance"](2);
          _angular_core__WEBPACK_IMPORTED_MODULE_5__["ɵɵclassMapInterpolate1"]("mr-r-10 grad-btn ", ctx.constants.approve_btn_class, "");
        }
      },
      dependencies: [_angular_forms__WEBPACK_IMPORTED_MODULE_6__["ɵNgNoValidate"], _angular_forms__WEBPACK_IMPORTED_MODULE_6__.NgControlStatusGroup, _angular_forms__WEBPACK_IMPORTED_MODULE_6__.FormGroupDirective, _angular_flex_layout_flex__WEBPACK_IMPORTED_MODULE_13__.DefaultLayoutDirective, _angular_material_radio__WEBPACK_IMPORTED_MODULE_21__.MatRadioButton, _angular_material_dialog__WEBPACK_IMPORTED_MODULE_8__.MatDialogTitle, _angular_material_dialog__WEBPACK_IMPORTED_MODULE_8__.MatDialogActions, _angular_material_dialog__WEBPACK_IMPORTED_MODULE_8__.MatDialogContent, _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatTable, _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatHeaderCellDef, _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatHeaderRowDef, _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatColumnDef, _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatCellDef, _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatRowDef, _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatHeaderCell, _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatCell, _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatHeaderRow, _angular_material_table__WEBPACK_IMPORTED_MODULE_7__.MatRow],
      styles: [_c8]
    });
  }
  return AllocRegenerateComponentpg;
})();

/***/ }),

/***/ 14092:
/*!**********************************************!*\
  !*** ./src/app/modules/allocation.module.ts ***!
  \**********************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   AllocationModule: () => (/* binding */ AllocationModule)
/* harmony export */ });
/* harmony import */ var _angular_common__WEBPACK_IMPORTED_MODULE_8__ = __webpack_require__(/*! @angular/common */ 60316);
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! @angular/router */ 95072);
/* harmony import */ var _child_essential_module__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./child.essential.module */ 62292);
/* harmony import */ var _directives_module__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./directives.module */ 25144);
/* harmony import */ var _components_transaction_annual_allocation_entry_annual_allocation_entry_component__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ../components/transaction/annual-allocation-entry/annual-allocation-entry.component */ 10694);
/* harmony import */ var _components_transaction_monthly_allocation_entry_monthly_allocation_entry_component__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ../components/transaction/monthly-allocation-entry/monthly-allocation-entry.component */ 75754);
/* harmony import */ var _guards_authentication_guard_authentication_guard__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ../guards/authentication.guard/authentication.guard */ 40878);
/* harmony import */ var _components_transaction_monthly_allocation_entry_pg_monthly_allocation_entry_pg_component__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! ../components/transaction/monthly-allocation-entry-pg/monthly-allocation-entry-pg.component */ 96070);
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! @angular/core */ 37580);






// import { AllocEntModifyComponentpg, AllocRegenerateComponentpg, FieldstaffDetailsComponentpg, MonthlyAllocationEntryPgComponent } from '../components/transaction/monthly-allocation-entry-pg/monthly-allocation-entry-pg.component';




const routes = [{
  path: "entry",
  component: _components_transaction_annual_allocation_entry_annual_allocation_entry_component__WEBPACK_IMPORTED_MODULE_2__.AnnualAllocationEntryComponent,
  canActivate: [_guards_authentication_guard_authentication_guard__WEBPACK_IMPORTED_MODULE_4__.AuthenticationGuard]
}, {
  path: "monthlyEntry",
  component: _components_transaction_monthly_allocation_entry_monthly_allocation_entry_component__WEBPACK_IMPORTED_MODULE_3__.MonthlyAllocationEntryComponent,
  canActivate: [_guards_authentication_guard_authentication_guard__WEBPACK_IMPORTED_MODULE_4__.AuthenticationGuard]
}, {
  path: "monthlyEntrypg",
  component: _components_transaction_monthly_allocation_entry_pg_monthly_allocation_entry_pg_component__WEBPACK_IMPORTED_MODULE_5__.MonthlyAllocationEntryPgComponent,
  canActivate: [_guards_authentication_guard_authentication_guard__WEBPACK_IMPORTED_MODULE_4__.AuthenticationGuard]
}];
let AllocationModule = /*#__PURE__*/(() => {
  class AllocationModule {
    static ɵfac = function AllocationModule_Factory(__ngFactoryType__) {
      return new (__ngFactoryType__ || AllocationModule)();
    };
    static ɵmod = /*@__PURE__*/_angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵdefineNgModule"]({
      type: AllocationModule
    });
    static ɵinj = /*@__PURE__*/_angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵdefineInjector"]({
      imports: [_angular_router__WEBPACK_IMPORTED_MODULE_7__.RouterModule.forChild(routes), _angular_common__WEBPACK_IMPORTED_MODULE_8__.CommonModule, _child_essential_module__WEBPACK_IMPORTED_MODULE_0__.ChildEssentialsModule, _directives_module__WEBPACK_IMPORTED_MODULE_1__.DirectivesModule, _angular_router__WEBPACK_IMPORTED_MODULE_7__.RouterModule]
    });
  }
  return AllocationModule;
})();
(function () {
  (typeof ngJitMode === "undefined" || ngJitMode) && _angular_core__WEBPACK_IMPORTED_MODULE_6__["ɵɵsetNgModuleScope"](AllocationModule, {
    declarations: [_components_transaction_annual_allocation_entry_annual_allocation_entry_component__WEBPACK_IMPORTED_MODULE_2__.AnnualAllocationEntryComponent, _components_transaction_annual_allocation_entry_annual_allocation_entry_component__WEBPACK_IMPORTED_MODULE_2__.AnnualDetailComponent, _components_transaction_monthly_allocation_entry_pg_monthly_allocation_entry_pg_component__WEBPACK_IMPORTED_MODULE_5__.MonthlyAllocationEntryPgComponent, _components_transaction_monthly_allocation_entry_pg_monthly_allocation_entry_pg_component__WEBPACK_IMPORTED_MODULE_5__.AllocEntModifyComponentpg, _components_transaction_monthly_allocation_entry_pg_monthly_allocation_entry_pg_component__WEBPACK_IMPORTED_MODULE_5__.FieldstaffDetailsComponentpg, _components_transaction_monthly_allocation_entry_pg_monthly_allocation_entry_pg_component__WEBPACK_IMPORTED_MODULE_5__.AllocRegenerateComponentpg],
    imports: [_angular_router__WEBPACK_IMPORTED_MODULE_7__.RouterModule, _angular_common__WEBPACK_IMPORTED_MODULE_8__.CommonModule, _child_essential_module__WEBPACK_IMPORTED_MODULE_0__.ChildEssentialsModule, _directives_module__WEBPACK_IMPORTED_MODULE_1__.DirectivesModule],
    exports: [_angular_router__WEBPACK_IMPORTED_MODULE_7__.RouterModule]
  });
})();

/***/ })

}]);
//# sourceMappingURL=92.js.map