package com.excel.bean;

import java.util.List;

public class EInvoiceByIrnResponse {
	 private String TransId;
	 private String TransName;
	 private String Distance;
     private String TransDocNo;
	 private String TransDocDt;
	 private String VehType;
	 private String Irn;
	 private String ewb_status;
	 private Govt_response govt_response;
	 public static class Govt_response{
		 private String Success;
		 private String EwbNo;
		 private String EwbDt;
		 private String EwbValidTill;
		 private List<ErrorDetails> ErrorDetails;
		public String getSuccess() {
			return Success;
		}
		public void setSuccess(String success) {
			Success = success;
		}
		public String getEwbNo() {
			return EwbNo;
		}
		public void setEwbNo(String ewbNo) {
			EwbNo = ewbNo;
		}
		public String getEwbDt() {
			return EwbDt;
		}
		public void setEwbDt(String ewbDt) {
			EwbDt = ewbDt;
		}
		public String getEwbValidTill() {
			return EwbValidTill;
		}
		public void setEwbValidTill(String ewbValidTill) {
			EwbValidTill = ewbValidTill;
		}
		 
		public List<ErrorDetails> getErrorDetails() {
			return ErrorDetails;
		}
		public void setErrorDetails(List<ErrorDetails> errorDetails) {
			ErrorDetails = errorDetails;
		}

		public class ErrorDetails{
			private String error_code;
			private String error_message;
			private String error_source;
			public String getError_code() {
				return error_code;
			}
			public void setError_code(String error_code) {
				this.error_code = error_code;
			}
			public String getError_message() {
				return error_message;
			}
			public void setError_message(String error_message) {
				this.error_message = error_message;
			}
			public String getError_source() {
				return error_source;
			}
			public void setError_source(String error_source) {
				this.error_source = error_source;
			}
			
		}
	   }

	public String getTransId() {
		return TransId;
	}

	public void setTransId(String transId) {
		TransId = transId;
	}

	public String getTransName() {
		return TransName;
	}

	public void setTransName(String transName) {
		TransName = transName;
	}

	public String getDistance() {
		return Distance;
	}

	public void setDistance(String distance) {
		Distance = distance;
	}

	public String getTransDocNo() {
		return TransDocNo;
	}

	public void setTransDocNo(String transDocNo) {
		TransDocNo = transDocNo;
	}

	public String getTransDocDt() {
		return TransDocDt;
	}

	public void setTransDocDt(String transDocDt) {
		TransDocDt = transDocDt;
	}

	public String getVehType() {
		return VehType;
	}

	public void setVehType(String vehType) {
		VehType = vehType;
	}

	public String getIrn() {
		return Irn;
	}

	public void setIrn(String irn) {
		Irn = irn;
	}

	public String getEwb_status() {
		return ewb_status;
	}

	public void setEwb_status(String ewb_status) {
		this.ewb_status = ewb_status;
	}

	public Govt_response getGovt_response() {
		return govt_response;
	}

	public void setGovt_response(Govt_response govt_response) {
		this.govt_response = govt_response;
	}
	 
	 
	 
}
