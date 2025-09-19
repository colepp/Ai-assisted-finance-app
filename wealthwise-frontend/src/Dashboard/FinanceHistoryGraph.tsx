import { useEffect } from "react";
import { getCookie } from "../Utils/Utils";


interface GraphProps {
    title: string; // Title of the graph
    timespanType: string; // Monthly, Yearly, etc.
    startDate: string; // Start date for the graph data
    endDate: string; // End date for the graph data
    data: Array<{ date: string; value: number }>; // Data points for the graph
}

const width = 800; // Width of the graph
const height = 400; // Height of the graph
const margin = { top: 20, right: 30, bottom: 30, left: 40 };

export default function FinanceHistoryGraph() {

    function drawGraph(data: GraphProps) {

    }

    useEffect(() => {
        const fetchData = async () => {
            const token = getCookie("auth-token");
            if (!token) {
                console.error("No auth token found");
                return;
            }
            const response = await fetch("/api/finance-history",{
                 method: "GET", 
                 headers: {
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${token}`
                 }
            );
            const data: GraphProps = await response.json();
            drawGraph(data);            // console.log(data);
        }
            );

}