import { api } from "../../Config/apiConfig";

export const makePaymentAction=(plan)=>async(dispatch)=>{
    try {
        const {data} = await api.post(`/api/plan/subscribe/${plan}`);

        if(data.paymentLink){
            window.location.href=data.paymentLink;
          }
          console.log("data",data)
        
      } catch (error) {
        console.log("catch error ",error)
      }
}

export const verifiedAccountAction=(paymentLinkId)=>async(dispatch)=>{
  try {
      const {data} = await api.get(`/api/plan/${paymentLinkId}`);
console.log("verified account ",data)
      
    } catch (error) {
      console.log("catch error ",error)
    }
}